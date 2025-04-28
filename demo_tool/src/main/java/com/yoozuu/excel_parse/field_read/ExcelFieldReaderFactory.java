package com.yoozuu.excel_parse.field_read;

import org.apache.commons.text.StringSubstitutor;

import java.util.HashMap;
import java.util.Map;

public class ExcelFieldReaderFactory {
    private static final Map<String, IExcelFieldReader> fieldReaderMap = new HashMap<>();
    static {
        fieldReaderMap.put("uint", new IntExcelFieldReader());
        fieldReaderMap.put("string", new StringExcelFieldReader());
        fieldReaderMap.put("array_int", new IntArrayExcelFieldReader());
        fieldReaderMap.put("vector3_array_int", new TripleListExcelFieldReader());
    }
    private static IExcelFieldReader getExcelFieldReader(String fieldType) {
        return fieldReaderMap.get(fieldType);
    }

    public static String readField(String fieldType, String fieldName) {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("fieldLowerUpper", fieldName);
        StringSubstitutor sub = new StringSubstitutor(valuesMap);
        String template = getExcelFieldReader(fieldType).fieldRead();
        return sub.replace(template);
    }

    public static String readContent(String fieldType, String configFieldName, String fieldName, boolean isReward) {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("field", configFieldName);
        valuesMap.put("fieldLowerUpper", fieldName);
        StringSubstitutor sub = new StringSubstitutor(valuesMap);
        String template = getExcelFieldReader(fieldType).contentRead();
        if (isReward) {
            template = getExcelFieldReader(fieldType).rewardRead();
        }
        return sub.replace(template);
    }
}
