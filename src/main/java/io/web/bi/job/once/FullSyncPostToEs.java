package io.web.bi.job.once;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

/**
 * 数据库初始化：在应用程序启动时自动创建表结构，并添加默认数据。
 * 缓存预热：在应用程序启动时加载一些常用数据到缓存中，提高系统性能。
 * 第三方服务初始化：在应用程序启动时初始化第三方服务的客户端，如消息队列、搜索引擎等。
 * 定时任务：在应用程序启动时启动定时任务，执行一些后台任务或定时处理业务逻辑。
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class FullSyncPostToEs implements CommandLineRunner {

    @Override
    public void run(String... args) {

    }
}
