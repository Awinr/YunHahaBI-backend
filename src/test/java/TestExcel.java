import com.alibaba.excel.EasyExcel;
import com.google.common.util.concurrent.RateLimiter;
import io.web.bi.AdminApplication;
import io.web.bi.mapper.ChartMapper;
import io.web.bi.model.entity.Chart;
import io.web.bi.service.ChartService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
public class TestExcel {
    /**
     * 同步的返回，不推荐使用，如果数据量大会把数据放到内存里面
     */
    @Resource
    public ChartMapper chartMapper;

    @Resource
    public ChartService chartService;

    @Test
    public void synchronousRead() {
        String fileName = "";

        // 这里 也可以不指定class，返回一个list，然后读取第一个sheet 同步读取会自动finish
        List<Map<Integer, String>> listMap = EasyExcel.read(fileName).headRowNumber(0).sheet().doReadSync();
        for (Map<Integer, String> data : listMap) {
            // 返回每条数据的键值对 表示所在的列 和所在列的值
            log.info("读取到数据:{}", data);
        }
    }

    @Test
    public void testMap() {
        Map<String, Object> map = new HashMap<>();
        Object obj = map.getOrDefault("a", "bb");
        map.putIfAbsent("a", obj);
        map.putIfAbsent("a", "cc");
        System.out.println();

        Object v = map.computeIfAbsent("tk", (k) -> {
            return "default";
        });

        Object v2 = map.computeIfAbsent("tk", (k) -> {
            return "default2";
        });
        System.out.println(v + " " + v2);
    }
    @Test
    public  void testRateLimiter() {
        //每秒限流5个请求
        RateLimiter limiter = RateLimiter.create(5.0);
        int count = 1;
        while(true){
            if(limiter.tryAcquire()){
                System.out.println(count++);
            }else{
                System.out.println("限流了");
            }
        }
    }
}
