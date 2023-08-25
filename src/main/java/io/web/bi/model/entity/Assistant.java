package io.web.bi.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 问答助手信息表
 * @TableName assistant
 */
@TableName(value ="assistant")
public class Assistant implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 问题名称
     */
    private String name;

    /**
     * 问题概述
     */
    private String goal;

    /**
     * 问答结果
     */
    private String questionRes;

    /**
     * wait,running,succeed,failed
     */
    private String status;

    /**
     * 执行信息
     */
    private String execMessage;

    /**
     * 问题类型, 见dict
     */
    private Long dictId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    public Long getId() {
        return id;
    }

    /**
     * id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 问题名称
     */
    public String getName() {
        return name;
    }

    /**
     * 问题名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 问题概述
     */
    public String getGoal() {
        return goal;
    }

    /**
     * 问题概述
     */
    public void setGoal(String goal) {
        this.goal = goal;
    }

    /**
     * 问答结果
     */
    public String getQuestionRes() {
        return questionRes;
    }

    /**
     * 问答结果
     */
    public void setQuestionRes(String questionRes) {
        this.questionRes = questionRes;
    }

    /**
     * wait,running,succeed,failed
     */
    public String getStatus() {
        return status;
    }

    /**
     * wait,running,succeed,failed
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 执行信息
     */
    public String getExecMessage() {
        return execMessage;
    }

    /**
     * 执行信息
     */
    public void setExecMessage(String execMessage) {
        this.execMessage = execMessage;
    }

    /**
     * 问题类型, 见dict
     */
    public Long getDictId() {
        return dictId;
    }

    /**
     * 问题类型, 见dict
     */
    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    /**
     * 创建用户 id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 创建用户 id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 是否删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 是否删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}