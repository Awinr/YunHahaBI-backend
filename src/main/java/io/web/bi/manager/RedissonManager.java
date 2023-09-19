package io.web.bi.manager;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;


/**
 * Guava的RateLimiter限流组件
 * 基于令牌桶算法实现
 * 为什么包叫manager呢，这个包下面的类提功了一个通用的功能，脱离于业务之外，放在其他项目里也可以用
 * @date 2023-06-07
 */
@Component
@AllArgsConstructor
@Slf4j
public class RedissonManager {

    private final RedissonClient redissonClient;

    /**
     * @param key 区分不同的限流器，比如根据用户的id来作区分
     * @return 获取成功返回true
     */
    public boolean tryAcquireRateLimit(String key) {
        // 1、 声明一个限流器，只初始化一次
        RRateLimiter rRateLimiter = redissonClient.getRateLimiter(key);

        // 2、 设置速率限制器的整体速率为每秒钟允许执行2次操作。
        // 这意味着在每秒钟内，该操作或资源最多只能被访问或使用2次。超过这个限制的访问将被限制或阻塞，直到下一个时间间隔开始。
        /**
         * RateType.OVERALL 无论有多少台服务器，都是放在一起去统计的
         */
        boolean trySetRate = rRateLimiter.trySetRate(RateType.OVERALL, 2, 1, RateIntervalUnit.SECONDS);
        if (trySetRate) {
            log.info("init rate = {}, interval = {}", rRateLimiter.getConfig().getRate(), rRateLimiter.getConfig().getRateInterval());
        }

        // 3、试图获取一个令牌，获取到返回true
        return rRateLimiter.tryAcquire(1);
    }
}
