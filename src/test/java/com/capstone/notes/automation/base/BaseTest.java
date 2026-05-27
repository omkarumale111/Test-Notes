package com.capstone.notes.automation.base;

import java.net.MalformedURLException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.capstone.notes.automation.config.ConfigReader;
import com.capstone.notes.automation.drivers.DriverFactory;
import com.capstone.notes.automation.hooks.TestListener;
@Listeners(TestListener.class)
public class BaseTest {

    @BeforeMethod
    public void setup()
            throws MalformedURLException {

        System.out.println("Opening browser");

        DriverFactory.initializeDriver();

        DriverFactory.getDriver().get(
                ConfigReader.getProperty("baseUrl"));
    }

    @AfterMethod
    public void tearDown() {

        System.out.println("Closing browser");

        DriverFactory.quitDriver();
    }
}