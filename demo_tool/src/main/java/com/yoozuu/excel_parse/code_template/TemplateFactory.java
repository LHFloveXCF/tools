package com.yoozuu.excel_parse.code_template;

import com.yoozuu.excel_parse.util.ExcelParseCheckUtil;
import util.FileUtil;

import java.util.List;

public class TemplateFactory {

    public static String getTemplateName(List<String> fieldRange) {
        int type = ExcelParseCheckUtil.getTemplateType(fieldRange);
        switch (type) {
            case 0:
                return FileUtil.readFileToString("demo_tool/src/main/java/com/yoozuu/excel_parse/code_template/config_over_sea_template");
            case 1:
                return FileUtil.readFileToString("demo_tool/src/main/java/com/yoozuu/excel_parse/code_template/config_over_sea_template_1");
            default:
                return "main";
        }
    }
}
