package com.yoozuu.excel_parse;

import com.yoozuu.excel_parse.code_template.TemplateFactory;
import com.yoozuu.excel_parse.constant.ExcelFieldRangeEnum;
import com.yoozuu.excel_parse.row_parse.RowParserFactory;
import com.yoozuu.excel_parse.util.ExcelParseCheckUtil;
import com.yoozuu.excel_parse.field_read.ExcelFieldReaderFactory;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.text.StringSubstitutor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.FileUtil;
import util.StringExchangeUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将配置表自动解析为代码类
 * 1. 格式固定： 第一行为表名；第二行为：注释；第三行为：字段名；第四行为：字段类型；第五行为：应用类型（只解析 all/server）
 * 2. 生成常用方法
 */
public class ExcelParseToCode {
    // 最终参数
    public static final Map<String, String> valuesMap = new HashMap<>();
    // 第一行数据
    public static final List<String> firstRowList = new ArrayList<>();
    // 字段注释
    public static final List<String> fieldCommentList = new ArrayList<>();
    // 字段名
    public static final List<String> fieldNameList = new ArrayList<>();
    // 字段类型
    public static final List<String> fieldTypeList = new ArrayList<>();
    // 应用范围
    public static final List<String> validRangList = new ArrayList<>();

    public static final String resultPath = "demo_tool/src/main/resources/result.txt";

    public static void main(String[] args) {
        try (InputStream fis = ExcelParseToCode.class.getClassLoader().getResourceAsStream("problem.xlsx")) {
            assert fis != null;
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                RowParserFactory.parse(row, getResultList(row));
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 生成参数
        parseConfigName();
        parseMainKey();
        parseOtherInfo();

        // 读取模板
        String template = TemplateFactory.getTemplateName(validRangList);
        StringSubstitutor sub = new StringSubstitutor(valuesMap);
        List<String> result = Lists.newArrayList();
        result.add(sub.replace(template));
        FileUtil.writeFile(resultPath, result);
        System.out.println("自动生成完毕");
    }

    /**
     * 解析主key
     */
    public static void parseMainKey() {
        int type = ExcelParseCheckUtil.getTemplateType(validRangList);
        String mainKey = "";
        String childKey = "";
        for (int i = 0; i < validRangList.size(); i++) {
            if (validRangList.get(i).equals(ExcelFieldRangeEnum.ALL_MAIN_KEY.value)) {
                mainKey = StringExchangeUtil.toLowerCamelCase(fieldNameList.get(i));
            }
            if (validRangList.get(i).equals(ExcelFieldRangeEnum.ALL_CHILD_KEY.value)) {
                childKey = StringExchangeUtil.toLowerCamelCase(fieldNameList.get(i));
            }
        }
        switch (type) {
            case 0:
                valuesMap.put("mainKeyStr", "${" + mainKey + "}_${" + childKey + "}");
                break;
            case 1:
                valuesMap.put("mainKeyStr", "${" + mainKey + "}");
                break;
            default:
                break;
        }
        valuesMap.put("mainKey", mainKey);
    }

    /**
     * 解析文件名+类名
     */
    public static void parseConfigName() {
        valuesMap.put("configName", firstRowList.get(0));
        valuesMap.put("configName2", firstRowList.get(1));
    }

    /**
     * 其它信息解析
     * 1. 解析字段
     * 2. 解析字段内容
     */
    public static void parseOtherInfo() {
        StringBuilder fieldsContent = new StringBuilder();
        StringBuilder readContent = new StringBuilder();
        for (int i = 0; i < validRangList.size(); i++) {
            String fieldRange = validRangList.get(i);
            if (!ExcelParseCheckUtil.needParse(fieldRange)) {
                continue;
            }
            String fieldType = fieldTypeList.get(i);
            String fieldName = fieldNameList.get(i);
            String fieldComment = "// " + fieldCommentList.get(i) + "\n";
            String fieldRead = ExcelFieldReaderFactory.readField(fieldType, StringExchangeUtil.toLowerCamelCase(fieldName));
            fieldsContent.append(fieldComment).append(fieldRead).append("\n");

            boolean isReward = fieldComment.contains("奖励");
            String fieldContent = ExcelFieldReaderFactory.readContent(fieldType, fieldName, StringExchangeUtil.toLowerCamelCase(fieldName), isReward);
            readContent.append(fieldContent).append("\n");
        }
        valuesMap.put("paramList", fieldsContent.toString());
        valuesMap.put("paramRead", readContent.toString());
    }

    /**
     * 根据行号，获取对应结果集
     */
    public static List<String> getResultList(Row row) {
        int rowNum = row.getRowNum();
        switch (rowNum) {
            case 0:
                return firstRowList;
            case 1:
                return fieldCommentList;
            case 2:
                return fieldNameList;
            case 3:
                return fieldTypeList;
            case 4:
                return validRangList;
            default:
                return null;
        }
    }
}
