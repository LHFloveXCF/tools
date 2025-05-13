package com.yoozuu.excel_parse.field_read;

public class StringExcelFieldReader implements IExcelFieldReader {
    @Override
    public String fieldRead() {
        return "var ${fieldLowerUpper}: String = \"\"; private set";
    }

    @Override
    public String contentRead() {
        return "${fieldLowerUpper} = row.readString(\"${field}\")";
    }
}
