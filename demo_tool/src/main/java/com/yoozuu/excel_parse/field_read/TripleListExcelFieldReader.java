package com.yoozuu.excel_parse.field_read;

public class TripleListExcelFieldReader implements IExcelFieldReader {
    @Override
    public String fieldRead() {
        return "var ${fieldLowerUpper}: ArrayList<Triple<Int, Int, Int>> = arrayListOf()";
    }

    @Override
    public String contentRead() {
        return "row.readVector3ArrayInt(\"${field}\").forEach { (level, param, value) ->\n" +
                "      ${fieldLowerUpper}.add(Triple(level, param, value))\n" +
                "    }";
    }

    @Override
    public String rewardRead() {
        return "${fieldLowerUpper} = row.readAssetPackage(listOf(\"${field}\"), manager)";
    }
}
