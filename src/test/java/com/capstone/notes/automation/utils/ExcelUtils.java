package com.capstone.notes.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    public static int getRowCount(String sheetName) {

        int rows = 0;

        try {

            FileInputStream file =
                    new FileInputStream(
                    "src/test/resources/testData/TestData.xlsx");

            workbook =
                    new XSSFWorkbook(file);

            sheet =
                    workbook.getSheet(sheetName);

            rows =
                    sheet.getLastRowNum();

            workbook.close();

        } catch(Exception e) {

            e.printStackTrace();
        }

        return rows;
    }
    public static String getCellData(String sheetName,
                                     int rowNum,
                                     int colNum) {

        String data = "";

        try {

            FileInputStream file =
            new FileInputStream(
            "src/test/resources/testData/TestData.xlsx");

            workbook = new XSSFWorkbook(file);

            sheet = workbook.getSheet(sheetName);

            Row row = sheet.getRow(rowNum);

            Cell cell = row.getCell(colNum);

            data = cell.toString();

            workbook.close();

        } catch(IOException e) {

            e.printStackTrace();
        }

        return data;
    }
}