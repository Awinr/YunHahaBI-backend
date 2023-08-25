package io.web.bi.manager;

import io.netty.util.concurrent.CompleteFuture;
import io.web.bi.config.ThreadPoolExecutorConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

@SpringBootTest
@Slf4j
class RedissonManagerTest {

    @Autowired
    private RedissonManager redissonManager;

    @Autowired
    private ThreadPoolExecutorConfig threadPoolExecutorConfig;

    @Test
    void tryAcquireRateLimit() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            service.execute(() -> {
                System.out.println(Thread.currentThread().getId());
                boolean res = redissonManager.tryAcquireRateLimit("rate_key");
                System.out.println(res);
            });
        }

        Object lock = new Object();
        synchronized (lock) {
            lock.wait();
        }
    }

    @Test
    void doRateLimit() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 30; i++) {
            final int index = i;
            service.execute(() -> {
                System.out.println(Thread.currentThread().getId());
                String key = "rate_key" + (index % 10);
                System.out.println(key);
//                redissonManager.doRateLimit(key);
            });
        }

        Object lock = new Object();
        synchronized (lock) {
            lock.wait();
        }
    }

    @Test
    void testCompletableFuture() throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            /*try{

            }catch (Exception e) {

            }*/
            String task = "task" + i;
            try {
                CompletableFuture.runAsync(() -> {
                    System.out.println(task);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, threadPoolExecutorConfig.threadPoolExecutor());
            }catch (Exception e) {
                log.error("error task: " + task);
            }
        }

        Object lock = new Object();
        synchronized (lock) {
            lock.wait();
        }
    }
}