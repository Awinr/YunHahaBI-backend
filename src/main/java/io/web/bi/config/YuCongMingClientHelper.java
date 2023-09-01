package io.web.bi.config;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.github.rholder.retry.Retryer;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import com.yupi.yucongming.dev.utils.SignUtils;
import io.web.bi.utils.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生成鱼聪明客户端
 *
 * @date 2023-06-07
 */
@Configuration
@Slf4j
public class YuCongMingClientHelper {

    @Value("${yuapi.client.access-key}")
    private String accessKey;

    @Value("${yuapi.client.secret-key}")
    private String secretKey;

    @Bean
    public YuCongMingClientHelper clientHelper() {
        return new YuCongMingClientHelper();
    }

    public BaseResponse<DevChatResponse> doChat(DevChatRequest devChatRequest) {
        String url = "https://www.yucongming.com/api/dev/chat";
        String json = JSONUtil.toJsonStr(devChatRequest);
        String result = null;
        Retryer<String> retryer = Retry.getRetryer();
        try {
            // 有异常会先进入重试机制，重试3次后，才会进入catch块
            result = retryer.call(() -> {
               return  ((HttpRequest) HttpRequest.post(url)
                .setConnectionTimeout(3000)
                .setReadTimeout(30)
                .addHeaders(this.getHeaderMap(json)))
                .body(json)
                .execute()
                .body();});
        } catch (Exception e) {
            log.error("request failed: " + e.getMessage(), e);
            return null;
        }

        TypeReference<BaseResponse<DevChatResponse>> typeRef = new TypeReference<BaseResponse<DevChatResponse>>() {};
        return (BaseResponse) JSONUtil.toBean(result, typeRef, false);
    }

    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> hashMap = new HashMap();
        hashMap.put("accessKey", this.accessKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        String encodedBody = URLEncodeUtil.encode(body, StandardCharsets.UTF_8);
        hashMap.put("body", encodedBody);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000L));
        hashMap.put("sign", SignUtils.genSign(encodedBody, this.secretKey));
        return hashMap;
    }
}
