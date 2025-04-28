package com.yoozuu.excel_parse.constant;

public enum ExcelFieldRangeEnum {
    SERVER("server", true),
    ALL("all", true),
    ALL_MAIN_KEY("allmainkey", true),
    ALL_CHILD_KEY("allchildkey", true),

    ;

    public final String value;
    public final Boolean needParse;
    ExcelFieldRangeEnum(String value, Boolean needParse) {
        this.value = value;
        this.needParse = needParse;
    }

    public static boolean needParse(String fieldRange) {
        for (ExcelFieldRangeEnum range : ExcelFieldRangeEnum.values()) {
            if (range.value.equals(fieldRange)) {
                return range.needParse;
            }
        }
        return false;
    }
}
