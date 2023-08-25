package io.web.bi.model.dto.chart;

import lombok.Data;

/**
 * 通过AI生成图表的请求
 */
@Data
public class GenChartByAIRequest {
    /**
     * 名称
     */
    private String name;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表数据
     */
    private String chartType;

    /**
     * dictId
     */
    private Long dictId;
}
