package io.web.bi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadPoolExecutorConfig.
 *
 * @date 2023-06-07
 */
@Configuration
@Slf4j
public class ThreadPoolExecutorConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadFactory threadFactory = new ThreadFactory() {
            // 这样可以避免多线程竞争条件下的数据不一致问题。
            private final AtomicInteger cnt = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                // 获取变量 cnt 的当前值，然后将其增加1，并返回变量的旧值。
                thread.setName("线程池线程" + cnt.getAndAdd(1));
//                thread.setUncaughtExceptionHandler(new CurrentThreadHandler());
                return thread;
            }
        };
        return new ThreadPoolExecutor(4, 4, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10), threadFactory);
    }

    public static class CurrentThreadHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            log.info("thread = {}, ex = {}", t.getId(), e);
        }
    }
}
