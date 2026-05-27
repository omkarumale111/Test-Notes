package com.capstone.notes.automation.hooks;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.capstone.notes.automation.utils.ScreenshotUtils;

public class TestListener
        implements ITestListener {

    @Override
    public void onTestFailure(
            ITestResult result) {

        try {

            // Just save screenshot to disk
            // AllureAttachmentListener handles
            // attaching it to Allure report
            ScreenshotUtils
                    .captureScreenshot(
                    result.getName());

        } catch (Exception e) {

            System.out.println(
                    "[TestListener] Screenshot"
                    + " capture failed: "
                    + e.getMessage());
        }
    }
}