package com.capstone.notes.automation.utils;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {

        int rows =
                ExcelUtils.getRowCount(
                "LoginData");

        Object[][] data =
                new Object[rows][3];

        for(int i=1;i<=rows;i++) {

            data[i-1][0] =
                    ExcelUtils.getCellData(
                    "LoginData",i,0);

            data[i-1][1] =
                    ExcelUtils.getCellData(
                    "LoginData",i,1);

            data[i-1][2] =
                    ExcelUtils.getCellData(
                    "LoginData",i,2);
        }

        return data;
    }
}