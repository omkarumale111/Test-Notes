package com.capstone.notes.automation.tests.e2e;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.capstone.notes.automation.config.ConfigReader;
import com.capstone.notes.automation.drivers.DriverFactory;
import com.capstone.notes.automation.pages.LoginPage;
import com.capstone.notes.automation.pages.NotesPage;
import com.capstone.notes.automation.utils.ExcelReader;

public class E2ETest {

    LoginPage loginPage;

    NotesPage notesPage;

    @BeforeMethod
    public void setup()
            throws Exception {

        DriverFactory.initializeDriver();

        loginPage =
                new LoginPage();

        notesPage =
                new NotesPage();
    }

    @Test
    public void completeWorkflowTest() {
    	String email =
    	        "varadvtelang04@gmail.com";

    	String password =
    	        "abcdef";

    	String category =
    	        ExcelReader.getCellData(
    	        "NotesData",
    	        "E2EWorkflow",
    	        "Category");

    	String title =
    	        ExcelReader.getCellData(
    	        "NotesData",
    	        "E2EWorkflow",
    	        "Title")
    	        + "_"
    	        + System.currentTimeMillis();

    	String description =
    	        ExcelReader.getCellData(
    	        "NotesData",
    	        "E2EWorkflow",
    	        "Description");

    	String updatedTitle =
    	        title
    	        + "_Updated";

    	String updatedDescription =
    	        description
    	        + "_Updated";
        DriverFactory
                .getDriver()
                .get(
                "https://practice.expandtesting.com/notes/app");

        // Login
        loginPage.login(
                email,
                password);

        // Create
        notesPage.createNote(
                "Home",
                title,
                description);

        Assert.assertTrue(
                notesPage.isNotePresent());

        // Search
        notesPage.searchNote(
                title);

        Assert.assertTrue(
                notesPage.isSearchSuccessful());

        // Edit
        notesPage.editNote(
                updatedTitle,
                updatedDescription);

        Assert.assertTrue(
                notesPage.isEditSuccessful(
                updatedTitle));

        // Delete
        notesPage.deleteNote(
                updatedTitle);

        Assert.assertTrue(
                notesPage.isDeleteSuccessful(
                updatedTitle));

        // Logout
        notesPage.logout();

        System.out.println(
                "Complete E2E workflow passed");
    }

    @AfterMethod
    public void tearDown() {

        DriverFactory.quitDriver();
    }
}