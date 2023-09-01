package io.web.bi.utils;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Aaron
 * @date 2023/9/1&16:35
 */
public class Retry {

    public static Retryer<String> getRetryer() {
        Retryer<String> retryer = RetryerBuilder.<String>newBuilder()
                .retryIfException()
                .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))
                // 当第一次请求失败后，Retryer会尝试再执行2次，总共3次请求机会。
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .build();
        return retryer;
    }
}
