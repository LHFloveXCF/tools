package org.example;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    try (FileInputStream fis = new FileInputStream(new File("C:\\yoozuu\\myJob\\excel\\src\\main\\resources\\problem.xlsx"))) {
      Workbook workbook = new XSSFWorkbook(fis); // 对于.xlsx文件使用XSSFWorkbook
      Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表

      for (Row row : sheet) {
        for (Cell cell : row) {
          switch (cell.getCellType()) {
            case STRING:
              System.out.print(cell.getStringCellValue() + "\t");
              break;
            case NUMERIC:
              System.out.print(cell.getNumericCellValue() + "\t");
              break;
            case BOOLEAN:
              System.out.print(cell.getBooleanCellValue() + "\t");
              break;
            // 其他类型...
            default:
              System.out.print("\t");
          }
        }
        System.out.println();
      }

      workbook.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}