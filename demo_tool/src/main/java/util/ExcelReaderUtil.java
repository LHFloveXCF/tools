package util;

import com.yoozuu.annotion.ExcelColumn;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelReaderUtil {

    /**
     * 解析 Excel 文件并映射到实体类列表
     * @param file Excel 文件
     * @param clazz 实体类的 Class 对象
     * @param <T> 实体类类型
     * @param skipLines 需要跳过的行数，默认为 0
     * @return 包含实体类对象的列表
     * @throws IOException 文件读取异常
     * @throws IllegalAccessException 反射访问属性异常
     * @throws InstantiationException 实例化对象异常
     * @throws ParseException 日期解析异常
     */
    public static <T> List<T> parseExcelToEntity(File file, Class<T> clazz, Integer skipLines) throws IOException, IllegalAccessException, InstantiationException, ParseException {
        try (InputStream inputStream = Files.newInputStream(file.toPath());
             Workbook workbook = getWorkbook(inputStream, file.getName())) {
            return parseWorkbookToEntity(workbook, clazz, skipLines);
        }
    }

    /**
     * 根据文件扩展名创建对应的 Workbook 对象
     * @param inputStream 文件输入流
     * @param fileName 文件名
     * @return Workbook 对象
     * @throws IOException 文件读取异常
     */
    private static Workbook getWorkbook(InputStream inputStream, String fileName) throws IOException {
        if (fileName.endsWith(".xls")) {
            return new HSSFWorkbook(inputStream);
        } else if (fileName.endsWith(".xlsx")) {
            return new XSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("不支持的文件格式，仅支持 .xls 和 .xlsx 格式");
        }
    }

    /**
     * 解析 Workbook 对象并映射到实体类列表
     *
     * @param <T>       实体类类型
     * @param workbook  Workbook 对象
     * @param clazz     实体类的 Class 对象
     * @param skipLines 需要跳过的行数，默认为 0
     * @return 包含实体类对象的列表
     * @throws IllegalAccessException 反射访问属性异常
     * @throws InstantiationException 实例化对象异常
     * @throws ParseException         日期解析异常
     */
    private static <T> List<T> parseWorkbookToEntity(Workbook workbook, Class<T> clazz, Integer skipLines) throws IllegalAccessException, InstantiationException, ParseException {
        List<T> result = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        Field[] fields = clazz.getDeclaredFields();

        for (Row row : sheet) {
            if (row.getRowNum() <= skipLines) continue;
            T obj = clazz.newInstance();
            for (Field field : fields) {
                if (field.isAnnotationPresent(ExcelColumn.class)) {
                    ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                    int index = excelColumn.index();
                    Cell cell = row.getCell(index);
                    if (cell != null) {
                        String cellValue = getCellValueAsString(cell);
                        field.setAccessible(true);
                        setFieldValue(obj, field, cellValue);
                    }
                }
            }
            result.add(obj);
        }
        return result;
    }

    /**
     * 将单元格的值转换为字符串
     * @param cell 单元格对象
     * @return 单元格的值对应的字符串
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return String.format("%d", (long) numericValue);
                    } else {
                        return String.format("%f", numericValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                CellValue cellValue = evaluator.evaluate(cell);
                switch (cellValue.getCellType()) {
                    case STRING:
                        return cellValue.getStringValue();
                    case NUMERIC:
                        double value = cellValue.getNumberValue();
                        if (value == (long) value) {
                            return String.format("%d", (long) value);
                        } else {
                            return String.format("%f", value);
                        }
                    case BOOLEAN:
                        return String.valueOf(cellValue.getBooleanValue());
                    default:
                        return "";
                }
            default:
                return "";
        }
    }

    /**
     * 设置实体类属性的值
     * @param obj 实体类对象
     * @param field 属性对象
     * @param value 属性值
     * @throws IllegalAccessException 反射访问属性异常
     * @throws ParseException 日期解析异常
     */
    private static void setFieldValue(Object obj, Field field, String value) throws IllegalAccessException, ParseException {
        Class<?> fieldType = field.getType();
        if (String.class == fieldType) {
            field.set(obj, value);
        } else if (Integer.class == fieldType || int.class == fieldType) {
            field.set(obj, Integer.parseInt(value));
        } else if (Long.class == fieldType || long.class == fieldType) {
            field.set(obj, Long.parseLong(value));
        } else if (Double.class == fieldType || double.class == fieldType) {
            field.set(obj, Double.parseDouble(value));
        } else if (Boolean.class == fieldType || boolean.class == fieldType) {
            field.set(obj, Boolean.parseBoolean(value));
        } else if (Date.class == fieldType) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", java.util.Locale.US);
            field.set(obj, sdf.parse(value));
        }
    }
}