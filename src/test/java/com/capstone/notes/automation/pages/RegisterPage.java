package com.capstone.notes.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.capstone.notes.automation.drivers.DriverFactory;

public class RegisterPage {

    WebDriver driver =
            DriverFactory.getDriver();

    // Locators

    By createAccountButton =
            By.cssSelector(
            "[data-testid='open-register-view']");

    By nameField =
            By.cssSelector(
            "[data-testid='register-name']");

    By emailField =
            By.cssSelector(
            "[data-testid='register-email']");

    By passwordField =
            By.cssSelector(
            "[data-testid='register-password']");

    By confirmPasswordField =
            By.cssSelector(
            "[data-testid='register-confirm-password']");

    By registerButton =
            By.cssSelector(
            "[data-testid='register-submit']");


    // Helper: JS click

    private void jsClick(
            By locator) {

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(10));

        WebElement element =
                wait.until(
                ExpectedConditions
                .elementToBeClickable(
                locator));

        ((JavascriptExecutor) driver)
                .executeScript(
                "arguments[0].click();",
                element);
    }


    // Helper: safe typing

    private void clearAndType(
            By locator,
            String text) {

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(10));

        WebElement element =
                wait.until(
                ExpectedConditions
                .visibilityOfElementLocated(
                locator));

        element.clear();

        element.sendKeys(
                text);
    }


    // Register

    public void registerUser(
            String name,
            String email,
            String password) {

        System.out.println(
                "[registerUser] Opening registration");

        jsClick(
                createAccountButton);

        System.out.println(
                "[registerUser] Entering data");

        clearAndType(
                nameField,
                name);

        clearAndType(
                emailField,
                email);

        clearAndType(
                passwordField,
                password);

        clearAndType(
                confirmPasswordField,
                password);

        System.out.println(
                "[registerUser] Clicking register");

        jsClick(
                registerButton);
    }
}