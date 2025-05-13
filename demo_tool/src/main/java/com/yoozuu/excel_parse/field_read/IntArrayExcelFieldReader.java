package com.yoozuu.excel_parse.field_read;

public class IntArrayExcelFieldReader implements IExcelFieldReader {
    @Override
    public String fieldRead() {
        return "lateinit var ${fieldLowerUpper}: IntArray; private set";
    }

    @Override
    public String contentRead() {
        return "${fieldLowerUpper} = row.readIntArray(\"${field}\")";
    }
}
