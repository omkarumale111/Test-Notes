package com.capstone.notes.automation.drivers;

import java.net.MalformedURLException;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.capstone.notes.automation.config.ConfigReader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();

    public static void initializeDriver()
            throws MalformedURLException {

    	ChromeOptions options;

    	String browser =
    	        ConfigReader.getProperty("browser");

    	if(browser.equalsIgnoreCase("chrome")) {

    	    options = new ChromeOptions();

    	} else {

    	    throw new RuntimeException(
    	        "Browser not supported: " + browser);
    	}

        boolean grid =
                Boolean.parseBoolean(
                ConfigReader.getProperty("grid"));

       

        if(grid) {

            driver.set(
                    new RemoteWebDriver(
                    new URL("http://localhost:4444"),
                    options));

        } else {

            WebDriverManager.chromedriver().setup();

            driver.set(
                    new ChromeDriver(options));
        }

        getDriver().manage().window().maximize();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {

        if(driver.get()!=null) {

            driver.get().quit();
            driver.remove();
        }
    }
}