package com.capstone.notes.automation.hooks;

import io.qameta.allure.Allure;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

import com.capstone.notes.automation.drivers.DriverFactory;

public class AllureAttachmentListener
        implements TestLifecycleListener {

    @Override
    public void beforeTestStop(
            TestResult result) {

        if (result.getStatus()
                == Status.FAILED) {

            try {

                if (DriverFactory
                        .getDriver() != null) {

                    byte[] screenshotBytes =
                            ((TakesScreenshot)
                            DriverFactory
                            .getDriver())
                            .getScreenshotAs(
                            OutputType.BYTES);

                    Allure.addAttachment(
                            "Failure Screenshot",
                            "image/png",
                            new ByteArrayInputStream(
                            screenshotBytes),
                            ".png");

                    System.out.println(
                            "[AllureAttachmentListener]"
                            + " Screenshot attached");
                }

            } catch (Exception e) {

                System.out.println(
                        "[AllureAttachmentListener]"
                        + " Screenshot failed: "
                        + e.getMessage());
            }
        }
    }
}