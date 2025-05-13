package com.yoozuu.excel_reader.entity;

import com.yoozuu.annotion.ExcelColumn;
import lombok.Data;

@Data
public class SkillEffectEntity {
    @ExcelColumn(index = 0)
    private Integer id;

    @ExcelColumn(index = 14)
    private String desc;
}