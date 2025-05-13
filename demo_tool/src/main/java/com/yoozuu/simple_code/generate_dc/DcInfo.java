package com.yoozuu.simple_code.generate_dc;

import lombok.Data;
import util.StringExchangeUtil;

import java.util.ArrayList;
import java.util.List;

@Data
public class DcInfo {
    private String tableName = "";
    private String key0 = "";
    private String key1 = "";
    private String[] primaryKeyFieldArr;
    // [0]表名[1]字段名
    private List<String> upperCamelCaseFields = new ArrayList<>();
    private List<String> lowerCamelCaseFields = new ArrayList<>();
    // 主键数量
    private int keyNum = 0;
    private String noteEntity = "";

    public void init() {
        upperCamelCaseFields.add(StringExchangeUtil.toUpperCamelCase(this.tableName));
        primaryKeyFieldArr = new String[keyNum];
        if (!key0.isEmpty()) {
            upperCamelCaseFields.add(StringExchangeUtil.toUpperCamelCase(key0));
            lowerCamelCaseFields.add(StringExchangeUtil.toLowerCamelCase(key0));
            primaryKeyFieldArr[0] = key0;
        }
        if (!key1.isEmpty()) {
            upperCamelCaseFields.add(StringExchangeUtil.toUpperCamelCase(key1));
            lowerCamelCaseFields.add(StringExchangeUtil.toLowerCamelCase(key1));
            primaryKeyFieldArr[1] = key1;
        }
    }

    public String getSqlKeyByNum(Integer num) {
        return primaryKeyFieldArr[num];
    }

    public String getEntityName() {
        return upperCamelCaseFields.get(0);
    }

    public String getEntityFieldName(Integer num) {
        return lowerCamelCaseFields.get(num);
    }
}
