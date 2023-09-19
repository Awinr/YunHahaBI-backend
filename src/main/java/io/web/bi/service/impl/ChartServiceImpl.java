package io.web.bi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.web.bi.model.entity.Chart;
import io.web.bi.service.ChartService;
import io.web.bi.mapper.ChartMapper;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* @description 针对表【chart(用户)】的数据库操作Service实现
* @createDate 2023-06-05 21:00:25
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

    @Resource
    ChartMapper chartMapper;

    @Override
    public void separateTable(MultipartFile multipartFile, String tableName) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 得到第一个sheet
        Sheet sheet = workbook.getSheetAt(0);
        // 读取表头，获得字段名
        Row headerRow = sheet.getRow(0);
        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            headers.add(cell.getStringCellValue());
        }
        // 读取第一行数据，获得数据类型
        List<String> dataTypes = new ArrayList<>();
        Row dataRow = sheet.getRow(1);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = dataRow.getCell(i);
            if (cell != null) {
                CellType cellType = cell.getCellType();
                switch (cellType) {
                    case STRING:
                        dataTypes.add("varchar(1024)");
                        break;
                    case NUMERIC:
                        double numericValue = cell.getNumericCellValue();
                        boolean isFloatingPoint = (numericValue % 1) != 0;
                        if (isFloatingPoint) {
                            dataTypes.add("decimal");
                        }else {
                            dataTypes.add("bigint");
                        }
                        break;
                }
            }
        }
        // 读取所有数据
        List<String> tableData = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row data = sheet.getRow(i);
            StringBuilder tableRowData = new StringBuilder();
            for (int j = 0; j < headers.size(); j++) {
                Cell cell = data.getCell(j);
                String value = null;
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case STRING:
                            value = cell.getStringCellValue();
                            tableRowData.append("\"").append(value).append("\"").append(",");
                            break;
                        case NUMERIC:
                            value = String.valueOf(cell.getNumericCellValue());
                            tableRowData.append(value).append(",");
                            break;
                    }
                }
            }
            if (tableRowData.length() > 0 && tableRowData.charAt(tableRowData.length() - 1) == ',') {
                tableRowData.deleteCharAt(tableRowData.length() - 1);
            }
            tableData.add(new String(tableRowData));
        }
        List<String> columns = new ArrayList<>();
        for (int i = 0; i < headers.size(); i++) {
            columns.add(headers.get(i) + " " + dataTypes.get(i));
        }
        // 创建数据表
        chartMapper.createTable(tableName, columns);
        // 插入表数据
        chartMapper.insertTableData(tableName, tableData);
    }
}




