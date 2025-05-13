package com.yoozuu.excel_parse.row_parse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 * 其他行解析器
 */
public class OtherRowParser implements IRowParser {
    public void parse(Row row, List<String> result) {
        for (Cell cell : row) {
            result.add(cell.getStringCellValue());
        }
    }
}
