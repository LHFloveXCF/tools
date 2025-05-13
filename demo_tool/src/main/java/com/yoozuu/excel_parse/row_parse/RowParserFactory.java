package com.yoozuu.excel_parse.row_parse;

import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RowParserFactory {
    private static final Map<Integer, IRowParser> rowParserMap = new HashMap<>();

    static {
        rowParserMap.put(0, new FirstRowParser());
        rowParserMap.put(1, new OtherRowParser());
        rowParserMap.put(2, new OtherRowParser());
        rowParserMap.put(3, new OtherRowParser());
        rowParserMap.put(4, new OtherRowParser());
    }

    private static IRowParser getRowParser(Integer rowNum) {
        return rowParserMap.get(rowNum);
    }

    public static void parse(Row row, List<String> result) {
        IRowParser parser = getRowParser(row.getRowNum());
        if (parser == null) {
            return;
        }
        parser.parse(row, result);
    }
}
