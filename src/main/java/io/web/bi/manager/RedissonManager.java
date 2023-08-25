package io.web.bi.manager;

import io.web.bi.common.ErrorCode;
import io.web.bi.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * RedissionManager.
 *
 * @date 2023-06-07
 */
@Component
@AllArgsConstructor
@Slf4j
public class RedissonManager {

    private final RedissonClient redissonClient;

    /**
     * 尝试获取limit
     * {@link RRateLimiter#trySetRate(RateType, long, long, RateIntervalUnit)} 如果存在key，设置失败
     * RateType? OVERALL 所有RRateLimiter实例， PER_CLIENT同个redissonClient
     * @param key
     * @return 获取成功返回true
     */
    public boolean tryAcquireRateLimit(String key) {
        // 1、 声明一个限流器，只初始化一次
        RRateLimiter rRateLimiter = redissonClient.getRateLimiter(key);

        // 2、 设置速率限制器的整体速率为每秒钟允许执行2次操作。
        // 这意味着在每秒钟内，该操作或资源最多只能被访问或使用2次。超过这个限制的访问将被限制或阻塞，直到下一个时间间隔开始。
        boolean trySetRate = rRateLimiter.trySetRate(RateType.OVERALL, 2, 1, RateIntervalUnit.SECONDS);
        if (trySetRate) {
            log.info("init rate = {}, interval = {}", rRateLimiter.getConfig().getRate(), rRateLimiter.getConfig().getRateInterval());
        }

        // 3、试图获取一个令牌，获取到返回true
        return rRateLimiter.tryAcquire(1);
    }
}
