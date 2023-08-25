package io.web.bi.utils;

import com.alibaba.excel.EasyExcel;
import io.web.bi.common.ErrorCode;
import io.web.bi.exception.ThrowUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExcelUtils {

    /**
     * 解析excel数据转换为csv string
     *
     * @param file
     */
    public static String excelDataParse(MultipartFile file) {
        List<Map<String, String>> data = null;
        try {
            data = EasyExcel.read(file.getInputStream())
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ThrowUtils.throwIf(CollectionUtils.isEmpty(data), ErrorCode.PARAMS_ERROR, "文件数据为空");
        data = data.stream().filter(Objects::nonNull).collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        for (Map<String, String> d : data) {
            builder.append(String.join(",", d.values())).append("\n");
        }
        /**
         * 结果如下：
         * 日期,用户数
         * 1号,10人
         * 2号,20人
         * 3号,30人
         */
        return builder.toString();
    }
}
