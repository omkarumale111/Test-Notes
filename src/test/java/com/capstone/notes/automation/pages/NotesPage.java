package com.capstone.notes.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.capstone.notes.automation.drivers.DriverFactory;

public class NotesPage {

    WebDriver driver =
            DriverFactory.getDriver();

    // Locators — all verified against actual HTML
    By addNoteButton =
            By.xpath("//button[contains(text(),'Add Note')]");

    By titleField =
            By.cssSelector(
            "[data-testid='note-title']");

    By descriptionField =
            By.cssSelector(
            "[data-testid='note-description']");

    By submitButton =
            By.cssSelector("[data-testid='note-submit']");

    By noteCard =
            By.className("card");

    By searchField =
            By.cssSelector("[data-testid='search-input']");

    // Search button — must be clicked after typing
    By searchButton =
            By.cssSelector("[data-testid='search-btn']");

    By createdNote =
            By.className("card");

    By editButton =
            By.cssSelector("[data-testid='note-edit']");

    By deleteButton =
            By.cssSelector("[data-testid='note-delete']");

    By deleteDialog =
            By.cssSelector("[data-testid='note-delete-dialog']");

    By deleteConfirmButton =
            By.cssSelector("[data-testid='note-delete-confirm']");
    By categoryDropdown =
            By.cssSelector(
            "[data-testid='note-category']");
    By invalidTitleField =
            By.cssSelector(
            "#title.is-invalid");
    By completeCheckbox =
            By.cssSelector(
            "[data-testid='toggle-note-switch']");
    By noteTitle =
            By.cssSelector(
            "[data-testid='note-card-title']");

    By noteDescription =
            By.cssSelector(
            "[data-testid='note-card-description']");

    By logoutButton =
            By.cssSelector(
            "[data-testid='logout']");

    // ── Helpers ──────────────────────────────────────────────


    private void jsClick(By locator) {

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(10));

        WebElement element =
                wait.until(
                ExpectedConditions
                .elementToBeClickable(locator));

        ((JavascriptExecutor) driver)
                .executeScript(
                "arguments[0].click();",
                element);
    }


    private void clearAndType(
            By locator,
            String text) {

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions
                .visibilityOfElementLocated(locator));

        int attempts = 0;

        while (attempts < 5) {

            try {

                WebElement field =
                        driver.findElement(locator);

                // Scroll to center so ads don't block
                ((JavascriptExecutor) driver)
                        .executeScript(
                        "arguments[0].scrollIntoView("
                        + "{block: 'center'});",
                        field);

                Thread.sleep(300);

                field = driver.findElement(locator);
                field.clear();

                driver.findElement(locator)
                        .sendKeys(text);

                return;

            } catch (StaleElementReferenceException e) {

                attempts++;

                System.out.println(
                        "[clearAndType] StaleElement on attempt "
                        + attempts
                        + " for locator: "
                        + locator);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }

            } catch (InterruptedException ie) {

                Thread.currentThread().interrupt();
                return;
            }
        }

        throw new RuntimeException(
                "Could not interact with element: "
                + locator
                + " after 5 attempts");
    }
    private void setValueJS(
            By locator,
            String text) {

      

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(10));

        WebElement element =
                wait.until(
                ExpectedConditions
                .elementToBeClickable(
                locator));

        element.click();

        element.sendKeys(
                org.openqa.selenium.Keys.CONTROL + "a");

        element.sendKeys(
                org.openqa.selenium.Keys.DELETE);

        element.sendKeys(
                text);

   
    }
    private void waitForModalOpen() {

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions
                .visibilityOfElementLocated(
                titleField));
    }
    private void waitForPageToFullyLoad() {

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(20));

        wait.until(d ->
                ((JavascriptExecutor) d)
                .executeScript(
                "return document.readyState")
                .equals("complete"));

        wait.until(
                ExpectedConditions
                .visibilityOfElementLocated(addNoteButton));
    }
    public void selectCategory(
            String category) {

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(10));

        WebElement dropdown =
                wait.until(
                ExpectedConditions
                .visibilityOfElementLocated(
                categoryDropdown));

        Select select =
                new Select(
                dropdown);

        select.selectByVisibleText(
                category);
    }

    // ── Page Actions ─────────────────────────────────────────


    public void clickAddNote() {

        System.out.println(
                "[clickAddNote] Clicking Add Note");

        jsClick(addNoteButton);

        System.out.println(
                "[clickAddNote] Waiting for modal to open");

        waitForModalOpen();

        System.out.println(
                "[clickAddNote] Modal is open");
    }


    public void enterTitle(String title) {

        System.out.println(
                "[enterTitle] Entering: " + title);

        clearAndType(titleField, title);

        System.out.println(
                "[enterTitle] Done");
    }


    public void enterDescription(String description) {

        System.out.println(
                "[enterDescription] Entering: " + description);

        clearAndType(descriptionField, description);

        System.out.println(
                "[enterDescription] Done");
    }


    public void clickSubmit() {

        System.out.println(
                "[clickSubmit] Clicking submit button");

        jsClick(submitButton);

        System.out.println(
                "[clickSubmit] Waiting for page to fully reload");

        waitForPageToFullyLoad();

        System.out.println(
                "[clickSubmit] Page reloaded successfully");
    }


    public void createNote(
            String category,
            String title,
            String description) {

        clickAddNote();

        selectCategory(
                category);

        enterTitle(
                title);

        enterDescription(
                description);

        clickSubmit();
    }


    public boolean isNotePresent() {

        try {

            WebDriverWait wait =
                    new WebDriverWait(
                    driver,
                    Duration.ofSeconds(5));

            return wait.until(
                    ExpectedConditions
                    .visibilityOfElementLocated(noteCard))
                    .isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }


    public void searchNote(String noteTitle) {

        System.out.println(
                "[searchNote] Starting — query: '"
                + noteTitle + "'");

        // Wait for search field to be visible
        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(5));

        wait.until(
                ExpectedConditions
                .visibilityOfElementLocated(searchField));

        // Type into search field
        clearAndType(searchField, noteTitle);

        // Click Search button to apply filter
        jsClick(searchButton);

        // Wait for results to update
        try {
            Thread.sleep(500);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        System.out.println(
                "[searchNote] Done");
    }


    public boolean isSearchSuccessful() {

        try {

            return driver
                    .findElement(createdNote)
                    .isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }


    public void editNote(
            String newTitle,
            String newDescription) {

        System.out.println(
                "[editNote] Starting");

        // No scrollTo(0,0) — it was scrolling to top
        // of outer website pushing the app out of view
        waitForPageToFullyLoad();

        System.out.println(
                "[editNote] Page loaded — waiting for edit button");

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions
                .visibilityOfElementLocated(editButton));

        System.out.println(
                "[editNote] Edit button visible — clicking");

        jsClick(editButton);

        System.out.println(
                "[editNote] Edit button clicked — waiting for modal");

        waitForModalOpen();

        System.out.println(
                "[editNote] Modal open — entering new title");

        setValueJS(
                titleField,
                newTitle);

        System.out.println(
                "[editNote] Title entered — entering new description");

        setValueJS(
                descriptionField,
                newDescription);
        System.out.println(
                "[editNote] Description entered — clicking submit");

        clickSubmit();
        System.out.println(
                "[editNote] Submitted");

        searchNote(
                newTitle);

        System.out.println(
                "[editNote] Search completed");
    }


    public boolean isEditSuccessful(
            String updatedTitle) {

        try {

            searchNote(
                    updatedTitle);

            return driver
                    .findElements(noteCard)
                    .size() > 0;
        }

        catch(Exception e) {

            return false;
        }
    }

    public void deleteNote(String titleToDelete) {

        System.out.println(
                "[deleteNote] Starting — searching for: "
                + titleToDelete);

        searchNote(titleToDelete);

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(10));

        System.out.println(
                "[deleteNote] Waiting for delete button");

        wait.until(
                ExpectedConditions
                .visibilityOfElementLocated(deleteButton));

        System.out.println(
                "[deleteNote] Clicking delete button");

        jsClick(deleteButton);

        System.out.println(
                "[deleteNote] Waiting for confirmation dialog");

        wait.until(
                ExpectedConditions
                .visibilityOfElementLocated(deleteDialog));

        System.out.println(
                "[deleteNote] Dialog visible — clicking confirm");

        jsClick(deleteConfirmButton);

        waitForPageToFullyLoad();

        System.out.println(
                "[deleteNote] Done — page reloaded after delete");
    }


    public boolean isDeleteSuccessful(String deletedTitle) {

        try {

            System.out.println(
                    "[isDeleteSuccessful] Checking title gone: "
                    + deletedTitle);

            // Search for the deleted title specifically
            searchNote(deletedTitle);

            WebDriverWait wait =
                    new WebDriverWait(
                    driver,
                    Duration.ofSeconds(5));

            // If no cards found — deletion confirmed
            wait.until(
                    ExpectedConditions
                    .numberOfElementsToBe(
                    By.cssSelector(
                    "[data-testid='note-card']"),
                    0));

            System.out.println(
                    "[isDeleteSuccessful] Confirmed — no cards found");

            return true;

        } catch (Exception e) {

            System.out.println(
                    "[isDeleteSuccessful] FAILED — cards still present");

            return false;
        }
    }
    public boolean isTitleValidationDisplayed() {

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(5));

        try {

            wait.until(
                    ExpectedConditions
                    .visibilityOfElementLocated(
                    invalidTitleField));

            return true;
        }

        catch(Exception e) {

            return false;
        }
    }
    public void logout() {

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(5));

        wait.until(
                ExpectedConditions
                .elementToBeClickable(
                logoutButton));

        jsClick(
                logoutButton);
    }
    //===============used in hybrid testing================================================================
    public String getDisplayedTitle() {

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(5));

        return wait.until(
                ExpectedConditions
                .visibilityOfElementLocated(
                noteTitle))
                .getText();
    }


    public String getDisplayedDescription() {

        WebDriverWait wait =
                new WebDriverWait(
                driver,
                Duration.ofSeconds(5));

        return wait.until(
                ExpectedConditions
                .visibilityOfElementLocated(
                noteDescription))
                .getText();
    }



    
}