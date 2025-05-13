package com.yoozuu.simple_code.generate_dc;

import org.apache.commons.text.StringSubstitutor;
import util.FileUtil;
import util.PropertiesUtil;

import java.util.*;

/**
 * 生成项目中常用的DC代码
 * 用户输入关键信息；
 * 1. 数据库表名
 * 2. 联合主键字段名 多个用逗号分隔
 */
public class SimpleCodeDcGenerate {
    public static final Map<String, String> valuesMap = new HashMap<>();

    public static final String keyTemplate = "  @Id\n" +
            "  @Column(name = \"${key0}\")\n" +
            "  override var ${keyField0}: Long,\n" +
            "  @Id\n" +
            "  @Column(name = \"${key1}\")\n" +
            "  var ${keyField1}: Int";
    public static final String entityTemplate = "@Id\n" +
            "  @Column(name = \"${key0}\", length = 20, columnDefinition = \"bigint\")\n" +
            "  override var ${keyField0}: Long,\n" +
            "  @Id\n" +
            "  @Column(name = \"${key1}\",  length = 11)\n" +
            "  var ${keyField1}: Int = 1,";

    public static void main(String[] args) {
        PropertiesUtil<DcInfo> propertiesUtil = new PropertiesUtil<>();
        DcInfo dcInfo = propertiesUtil.loadConfig(DcInfo.class, "generate_dc.properties");
        dcInfo.init();

        System.out.println("-----------------------");
        generateSql(dcInfo);
        System.out.println("-----------------------");
        generateEntity(dcInfo);
        System.out.println("-----------------------");
        generateDC();
        System.out.println("生成结束");
    }

    private static void generateSql(DcInfo dcInfo) {
        valuesMap.put("tableName", dcInfo.getTableName());
        for (int i = 0; i < dcInfo.getKeyNum(); i++) {
            valuesMap.put("key" + i, dcInfo.getSqlKeyByNum(i));
        }
        StringSubstitutor sub = new StringSubstitutor(valuesMap);
        String template = FileUtil.readFileToString("demo_tool/src/main/java/com/yoozuu/simple_code/code_template/dc/simple_code_sql");
        String replace = sub.replace(template);
        System.out.println(replace);
    }

    private static void generateEntity(DcInfo dcInfo) {
        valuesMap.put("noteEntity", dcInfo.getNoteEntity());
        valuesMap.put("entityName", dcInfo.getEntityName());
        valuesMap.put("secondKeyField", dcInfo.getEntityFieldName(1));

        for (int i = 0; i < dcInfo.getKeyNum(); i++) {
            valuesMap.put("keyField" + i, dcInfo.getEntityFieldName(i));
        }
        StringSubstitutor sub = new StringSubstitutor(valuesMap);
        String pkParams = sub.replace(keyTemplate);
        String entityParams = sub.replace(entityTemplate);
        valuesMap.put("pkParams", pkParams);
        valuesMap.put("entityParams", entityParams);

        String template = FileUtil.readFileToString("demo_tool/src/main/java/com/yoozuu/simple_code/code_template/dc/simple_code_entity");
        String replace = sub.replace(template);
        System.out.println(replace);
    }
    private static void generateDC() {
        StringSubstitutor sub = new StringSubstitutor(valuesMap);
        String template = FileUtil.readFileToString("demo_tool/src/main/java/com/yoozuu/simple_code/code_template/dc/simple_code_dc");
        String replace = sub.replace(template);
        System.out.println(replace);
    }
}

