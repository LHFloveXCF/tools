package com.yoozuu.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记实体类属性对应 Excel 列索引的注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    /**
     * Excel 列索引，从 0 开始
     * @return 列索引
     */
    int index();
}