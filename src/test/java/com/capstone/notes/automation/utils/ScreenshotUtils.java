package com.capstone.notes.automation.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.capstone.notes.automation.drivers.DriverFactory;

public class ScreenshotUtils {

    public static String captureScreenshot(
            String testName) {

        WebDriver driver =
                DriverFactory.getDriver();

        File source =
                ((TakesScreenshot) driver)
                .getScreenshotAs(
                OutputType.FILE);

        String timestamp =
                new SimpleDateFormat(
                "yyyyMMdd_HHmmss")
                .format(new Date());

        File destination =
                new File(
                "src/test/resources/screenshots/"
                + testName + "_"
                + timestamp + ".png");

        try {

            Files.copy(
                    source.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            System.out.println(
                    "Screenshot saved: "
                    + destination.getAbsolutePath());

            return destination.getAbsolutePath();

        }

        catch(IOException e) {

            e.printStackTrace();

            return null;
        }
    }
}