package com.capstone.notes.automation.tests.ui;

import org.testng.Assert;

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import com.capstone.notes.automation.base.BaseTest;
import com.capstone.notes.automation.pages.LoginPage;
import org.testng.annotations.Listeners;
import com.capstone.notes.automation.hooks.TestListener;
import com.capstone.notes.automation.utils.TestDataProvider;
@Listeners(TestListener.class)
public class LoginTest extends BaseTest {

    LoginPage loginPage;

    @BeforeMethod
    public void initializePage() {

        loginPage = new LoginPage();
    }

    @Test(
        dataProvider="loginData",
        dataProviderClass=TestDataProvider.class)

    public void loginTest(
            String email,
            String password,
            String expectedResult) {

        loginPage.login(
                email,
                password);

        if(expectedResult.equalsIgnoreCase(
                "Valid")) {

            Assert.assertTrue(
                    loginPage.waitForSuccessfulLogin());

        } else {

            Assert.assertFalse(
                    loginPage.waitForSuccessfulLogin());
        }
    }
}
