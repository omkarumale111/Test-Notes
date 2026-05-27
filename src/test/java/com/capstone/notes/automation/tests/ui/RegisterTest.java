package com.capstone.notes.automation.tests.ui;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.capstone.notes.automation.config.ConfigReader;
import com.capstone.notes.automation.drivers.DriverFactory;
import com.capstone.notes.automation.pages.RegisterPage;
import com.capstone.notes.automation.utils.ExcelReader;

public class RegisterTest {

    RegisterPage registerPage;

    @BeforeClass
    public void initializePages()
            throws Exception {

        DriverFactory.initializeDriver();

        DriverFactory.getDriver().get(
                ConfigReader.getProperty(
                "baseUrl"));

        registerPage =
                new RegisterPage();
    }

    @AfterClass
    public void closeBrowser() {

        DriverFactory.quitDriver();
    }

    @Test
    public void registerTest() {

        System.out.println(
                "[registerTest] Starting");
        String name =
                ExcelReader.getCellData(
                "RegisterData",
                "RegisterUser",
                "Name");

        String emailPrefix =
                ExcelReader.getCellData(
                "RegisterData",
                "RegisterUser",
                "EmailPrefix");

        String password =
                ExcelReader.getCellData(
                "RegisterData",
                "RegisterUser",
                "Password");

        String uniqueEmail =
                emailPrefix
                + System.currentTimeMillis()
                + "@gmail.com";

        registerPage.registerUser(
                name,
                uniqueEmail,
                password);

        Assert.assertTrue(
                DriverFactory.getDriver()
                .getCurrentUrl()
                .contains("login")
                ||
                DriverFactory.getDriver()
                .getCurrentUrl()
                .contains("notes"));

        System.out.println(
                "[registerTest] PASSED");
    }
}