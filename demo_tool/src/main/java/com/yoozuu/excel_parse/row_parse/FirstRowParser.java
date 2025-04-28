package com.yoozuu.excel_parse.row_parse;

import org.apache.poi.ss.usermodel.Row;
import util.StringExchangeUtil;

import java.util.List;

public class FirstRowParser implements IRowParser {
    /**
     * 解析第一行 结果集中：下标0-表示代码类名；下标1-表示配置类名
     * @param row
     * @param result
     */
    public void parse(Row row, List<String> result) {
        String cellValue = row.getCell(0).getStringCellValue();
        String configName = StringExchangeUtil.toUpperCamelCase(cellValue);
        result.add(configName);
        result.add(cellValue);
    }
}
