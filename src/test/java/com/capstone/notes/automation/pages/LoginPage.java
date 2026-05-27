package com.capstone.notes.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.capstone.notes.automation.drivers.DriverFactory;

public class LoginPage {

    WebDriver driver = DriverFactory.getDriver();

    // Locators

    By loginButton =
            By.xpath("//a[text()='Login']");

    By emailField =
            By.name("email");

    By passwordField =
            By.name("password");

    By submitButton =
            By.xpath("//button[@type='submit']");

    By homePageHeader =
            By.xpath("//h1[contains(text(),'My Notes')]");


    // Click Login button

    public void clickLogin() {

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(5));

        WebElement button =
                wait.until(
                ExpectedConditions
                .elementToBeClickable(
                loginButton));

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].click();",
                button);
    }


    // Enter Email

    public void enterEmail(String email) {

        WebElement emailBox =
                driver.findElement(emailField);

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].scrollIntoView({block:'nearest'});",
                emailBox);

        emailBox.clear();

        emailBox.sendKeys(email);
    }

    public void enterPassword(String password) {

        WebElement passwordBox =
                driver.findElement(passwordField);

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].scrollIntoView({block:'nearest'});",
                passwordBox);

        passwordBox.clear();

        passwordBox.sendKeys(password);
    }

    // Click Submit button

    public void clickSubmit() {

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(5));

        WebElement button =
                wait.until(
                ExpectedConditions
                .elementToBeClickable(
                submitButton));

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].click();",
                button);
    }


    // Login method

    public void login(
            String email,
            String password) {

        clickLogin();

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(10));

        // wait for login form to actually appear
        wait.until(
                ExpectedConditions
                .visibilityOfElementLocated(
                emailField));

        enterEmail(
                email);

        enterPassword(
                password);

        clickSubmit();

        // wait for successful redirect
        wait.until(
                ExpectedConditions
                .or(
                    ExpectedConditions.urlContains(
                            "/notes/app"),
                    ExpectedConditions.urlContains(
                            "/login")
                ));
    }

    // Success verification

    public boolean isLoginSuccessful() {

        return driver
                .findElement(
                homePageHeader)
                .isDisplayed();
    }


    public String getCurrentUrl() {

        return driver.getCurrentUrl();
    }


    // Wait for successful login

    public boolean waitForSuccessfulLogin() {

        try {

            WebDriverWait wait =
                    new WebDriverWait(
                    driver,
                    Duration.ofSeconds(5));

            return wait.until(
                    ExpectedConditions.urlToBe(
                    "https://practice.expandtesting.com/notes/app"));

        }

        catch(Exception e) {

            return false;
        }
    }
}