package io.web.bi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.web.bi.model.entity.Chart;
import io.web.bi.model.entity.UserCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @description 针对表【chart(用户)】的数据库操作Mapper
* @createDate 2023-06-05 21:00:25
* @Entity io.web.bi.model.entity.Chart
*/
public interface ChartMapper extends BaseMapper<Chart> {
    void createTable(@Param("tableName") String tableName,
                     @Param("columns") List<String> columns
                     );
    boolean insertTableData(@Param("tableName") String tableName,
                            @Param("tableData") List<String> tableData);
    Chart selectBySingleTableName(@Param("id") Long id);
    Chart selectEntireByTableName(@Param("id") Long id);

}




