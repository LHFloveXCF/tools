package com.yoozuu.excel_parse.field_read;

public class IntExcelFieldReader implements IExcelFieldReader{
    @Override
    public String fieldRead() {
        return "var ${fieldLowerUpper}: Int = 0; private set";
    }

    @Override
    public String contentRead() {
        return "${fieldLowerUpper} = row.readInt(\"${field}\")";
    }
}
