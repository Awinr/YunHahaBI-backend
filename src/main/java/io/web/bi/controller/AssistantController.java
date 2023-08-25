package io.web.bi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import io.web.bi.annotation.AuthCheck;
import io.web.bi.common.BaseResponse;
import io.web.bi.common.DeleteRequest;
import io.web.bi.common.ErrorCode;
import io.web.bi.common.ResultUtils;
import io.web.bi.config.ModelIdProperties;
import io.web.bi.constant.CommonConstant;
import io.web.bi.constant.UserConstant;
import io.web.bi.exception.BusinessException;
import io.web.bi.exception.ThrowUtils;
import io.web.bi.manager.AIHelper;
import io.web.bi.manager.RedissonManager;
import io.web.bi.model.dto.assistant.AssistantAddRequest;
import io.web.bi.model.dto.assistant.AssistantEditRequest;
import io.web.bi.model.dto.assistant.AssistantQueryRequest;
import io.web.bi.model.dto.assistant.AssistantUpdateRequest;
import io.web.bi.model.dto.chart.GenChartByAIRequest;
import io.web.bi.model.entity.Assistant;
import io.web.bi.model.entity.User;
import io.web.bi.model.enums.ChartStatusEnum;
import io.web.bi.service.AssistantService;
import io.web.bi.service.UserService;
import io.web.bi.utils.SqlUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("assistant")
@AllArgsConstructor
@Slf4j
public class AssistantController {

    private final AssistantService assistantService;

    private final UserService userService;

    private final RedissonManager redissonManager;

//    private final AIHelper aiHelper;
//
//    private final ModelIdProperties modelIdProperties;

    private final RabbitTemplate rabbitTemplate;

    private final static Gson GSON = new Gson();


    // region 增删改查

    /**
     * 创建
     *
     * @param assistantAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addAssistant(@RequestBody AssistantAddRequest assistantAddRequest, HttpServletRequest request) {
        if (assistantAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Assistant assistant = new Assistant();
        BeanUtils.copyProperties(assistantAddRequest, assistant);
        User loginUser = userService.getLoginUser(request);
        assistant.setUserId(loginUser.getId());
        boolean result = assistantService.save(assistant);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newAssistantId = assistant.getId();
        return ResultUtils.success(newAssistantId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteAssistant(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Assistant oldAssistant = assistantService.getById(id);
        ThrowUtils.throwIf(oldAssistant == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldAssistant.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = assistantService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param assistantUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateAssistant(@RequestBody AssistantUpdateRequest assistantUpdateRequest) {
        if (assistantUpdateRequest == null || assistantUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Assistant assistant = new Assistant();
        BeanUtils.copyProperties(assistantUpdateRequest, assistant);
        long id = assistantUpdateRequest.getId();
        // 判断是否存在
        Assistant oldAssistant = assistantService.getById(id);
        ThrowUtils.throwIf(oldAssistant == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = assistantService.updateById(assistant);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<Assistant> getAssistantById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Assistant assistant = assistantService.getById(id);
        if (assistant == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(assistant);
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param assistantQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<Assistant>> listAssistantByPage(@RequestBody AssistantQueryRequest assistantQueryRequest,
                                                             HttpServletRequest request) {
        long current = assistantQueryRequest.getCurrent();
        long size = assistantQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Assistant> assistantPage = assistantService.page(new Page<>(current, size),
                getQueryWrapper(assistantQueryRequest));
        return ResultUtils.success(assistantPage);
    }

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param assistantQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page")
    public BaseResponse<Page<Assistant>> listMyAssistantByPage(@RequestBody AssistantQueryRequest assistantQueryRequest,
                                                               HttpServletRequest request) {
        if (assistantQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        assistantQueryRequest.setUserId(loginUser.getId());
        long current = assistantQueryRequest.getCurrent();
        long size = assistantQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Assistant> assistantPage = assistantService.page(new Page<>(current, size),
                getQueryWrapper(assistantQueryRequest));
        return ResultUtils.success(assistantPage);
    }

    /**
     * 编辑（用户）
     *
     * @param assistantEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editAssistant(@RequestBody AssistantEditRequest assistantEditRequest, HttpServletRequest request) {
        if (assistantEditRequest == null || assistantEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Assistant assistant = new Assistant();
        BeanUtils.copyProperties(assistantEditRequest, assistant);
        User loginUser = userService.getLoginUser(request);
        long id = assistantEditRequest.getId();
        // 判断是否存在
        Assistant oldAssistant = assistantService.getById(id);
        ThrowUtils.throwIf(oldAssistant == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldAssistant.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = assistantService.updateById(assistant);
        return ResultUtils.success(result);
    }

    /**
     * 获取查询包装类
     *
     * @param assistantQueryRequest
     * @return
     */
    private QueryWrapper<Assistant> getQueryWrapper(AssistantQueryRequest assistantQueryRequest) {
        QueryWrapper<Assistant> queryWrapper = new QueryWrapper<>();
        if (assistantQueryRequest == null) {
            return queryWrapper;
        }
        Long id = assistantQueryRequest.getId();
        String name = assistantQueryRequest.getName();
        String goal = assistantQueryRequest.getGoal();
        Long dictId = assistantQueryRequest.getDictId();
        Long userId = assistantQueryRequest.getUserId();
        String sortField = assistantQueryRequest.getSortField();
        String sortOrder = assistantQueryRequest.getSortOrder();
        String status = assistantQueryRequest.getStatus();

        queryWrapper.eq(id != null && id > 0, "id", id);
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.eq(StringUtils.isNotBlank(goal), "goal", goal);
        queryWrapper.eq(ObjectUtils.isNotEmpty(dictId), "dictId", dictId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(status), "status", status);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    // endregion

    // region AI相关

    /**
     * 软件开发助手对话
     * @param genChartByAIRequest
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/aiAssistant")
    public BaseResponse<?> aiAssistant(@RequestBody GenChartByAIRequest genChartByAIRequest, HttpServletRequest request) throws IOException {
        String name = genChartByAIRequest.getName();
        String goal = genChartByAIRequest.getGoal();
        Long dictId = genChartByAIRequest.getDictId();
        User loginUser = userService.getLoginUser(request);

        if (StringUtils.isBlank(name)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "问题名称为空");
        }

        if (ObjectUtils.isEmpty(dictId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "问题类型为空");
        }

        if (StringUtils.isBlank(goal)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "问题描述为空");
        }
        boolean tryAcquireRateLimit = redissonManager.tryAcquireRateLimit("AI_Rate_" + loginUser.getId());
        if (!tryAcquireRateLimit) {
            throw new BusinessException(ErrorCode.TOO_MANY_REQUEST);
        }

        Assistant assistant = new Assistant();
        assistant.setName(name);
        assistant.setGoal(goal);
        assistant.setDictId(dictId);
        assistant.setStatus(ChartStatusEnum.WAIT.getValue());
        assistant.setUserId(loginUser.getId());
        boolean save = assistantService.save(assistant);

        String json = GSON.toJson(assistant);
        // 发送到消息队列
        rabbitTemplate.convertAndSend("qaDirectExchange", "qaRouting", json);

        // 插入到数据库
        ThrowUtils.throwIf(!save, ErrorCode.SYSTEM_ERROR, "保存失败");
        return ResultUtils.success(assistant);
    }
    // endregion
}
