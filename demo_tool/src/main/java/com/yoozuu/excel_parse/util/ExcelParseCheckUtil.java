package com.yoozuu.excel_parse.util;

import com.yoozuu.excel_parse.constant.ExcelFieldRangeEnum;

import java.util.List;

public class ExcelParseCheckUtil {
    public static boolean needParse(String fieldRange) {
        return ExcelFieldRangeEnum.needParse(fieldRange);
    }

    public static Integer getTemplateType(List<String> fieldRange) {
        return fieldRange.contains(ExcelFieldRangeEnum.ALL_CHILD_KEY.value) ? 0 : 1;
    }
}
