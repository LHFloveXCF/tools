package com.yoozuu.excel_parse.field_read;

public interface IExcelFieldReader {
    String fieldRead();

    String contentRead();

    default String rewardRead() {
        return "";
    }
}
