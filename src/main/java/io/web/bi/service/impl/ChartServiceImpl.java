package io.web.bi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.web.bi.model.entity.Chart;
import io.web.bi.service.ChartService;
import io.web.bi.mapper.ChartMapper;
import org.springframework.stereotype.Service;

/**
* @description 针对表【chart(用户)】的数据库操作Service实现
* @createDate 2023-06-05 21:00:25
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

}




