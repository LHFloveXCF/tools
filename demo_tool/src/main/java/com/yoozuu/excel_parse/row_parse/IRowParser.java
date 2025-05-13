package com.yoozuu.excel_parse.row_parse;

import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public interface IRowParser {
    void parse(Row row, List<String> result);
}
