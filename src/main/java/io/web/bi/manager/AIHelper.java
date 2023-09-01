package io.web.bi.manager;

import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import io.web.bi.common.ErrorCode;
import io.web.bi.config.YuCongMingClientHelper;
import io.web.bi.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

/**
 * 利用鱼聪明客户端助手进行AI调用
 *
 * @date 2023-06-07
 */
@Configuration
@AllArgsConstructor
public class AIHelper {

    private final YuCongMingClientHelper yuCongMingClientHelper;

    /**
     * AI 对话
     *
     * @param modelId
     * @param message
     * @return
     */
    public String doChat(Long modelId, String message) {
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(modelId);
        devChatRequest.setMessage(message);
        BaseResponse<DevChatResponse> response = yuCongMingClientHelper.doChat(devChatRequest);
        if (response == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "AI 响应错误");
        }
        return response.getData().getContent();
    }
}
