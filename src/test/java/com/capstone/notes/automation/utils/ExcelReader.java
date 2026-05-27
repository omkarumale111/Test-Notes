package com.capstone.notes.automation.utils;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    public static String getCellData(
            String sheetName,
            String scenarioName,
            String columnName) {

        try {

            FileInputStream file =
                    new FileInputStream(
                    "src/test/resources/testdata/TestData.xlsx");

            Workbook workbook =
                    new XSSFWorkbook(
                    file);

            Sheet sheet =
                    workbook.getSheet(
                    sheetName);

            Row headerRow =
                    sheet.getRow(0);

            int columnIndex = -1;

            // Find column using header name
            for(int i = 0;
                i < headerRow.getLastCellNum();
                i++) {

                Cell cell =
                        headerRow.getCell(i);

                if(cell.toString()
                        .equalsIgnoreCase(
                        columnName)) {

                    columnIndex = i;

                    break;
                }
            }

            if(columnIndex == -1) {

                workbook.close();

                throw new RuntimeException(
                        "Column not found: "
                        + columnName);
            }

            // Find row using Scenario column
            for(int i = 1;
                i <= sheet.getLastRowNum();
                i++) {

                Row row =
                        sheet.getRow(i);

                String scenario =
                        row.getCell(0)
                        .toString();

                if(scenario.equalsIgnoreCase(
                        scenarioName)) {

                    String value =
                            row.getCell(
                            columnIndex)
                            .toString();

                    workbook.close();

                    return value;
                }
            }

            workbook.close();

            throw new RuntimeException(
                    "Scenario not found: "
                    + scenarioName);
        }

        catch(Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}