package io.web.bi.manager;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @author Aaron
 * @date 2023/10/10&18:42
 */
public class TestCallable {
    @Test
    public void testCallable() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Callable<String> myCallable = new MyCallable();
        Future<String> submit = executorService.submit(new MyCallable());
        try {
            System.out.println(submit.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "任务执行完成";
    }
}
