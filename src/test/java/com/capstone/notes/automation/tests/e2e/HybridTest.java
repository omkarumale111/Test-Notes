package com.capstone.notes.automation.tests.e2e;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.capstone.notes.automation.api.AuthAPI;
import com.capstone.notes.automation.api.NotesAPI;
import com.capstone.notes.automation.config.ConfigReader;
import com.capstone.notes.automation.drivers.DriverFactory;
import com.capstone.notes.automation.pages.LoginPage;
import com.capstone.notes.automation.pages.NotesPage;
import com.capstone.notes.automation.utils.ExcelReader;

import io.restassured.response.Response;

public class HybridTest {

    AuthAPI authAPI =
            new AuthAPI();

    NotesAPI notesAPI =
            new NotesAPI();

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
    public void validateCreatedNoteViaApiAppearsInUI() {

        String token =
                authAPI.login(
                "varadvtelang04@gmail.com",
                "abcdef");

        String category =
                ExcelReader.getCellData(
                "ApiData",
                "CreateNoteViaAPI",
                "Category");

        String title =
                ExcelReader.getCellData(
                "ApiData",
                "CreateNoteViaAPI",
                "Title");

        String description =
                ExcelReader.getCellData(
                "ApiData",
                "CreateNoteViaAPI",
                "Description");

        notesAPI.createNote(
                token,
                category,
                title,
                description);

        DriverFactory
                .getDriver()
                .get(
                "https://practice.expandtesting.com/notes/app");

        loginPage.login(
                "varadvtelang04@gmail.com",
                "abcdef");

        notesPage.searchNote(
                title);

        Assert.assertTrue(
                notesPage.isNotePresent());

        System.out.println(
                "Hybrid Test Passed");
    }
    
    @Test
    public void validateUiApiDataConsistency() {

        String email =
                "varadvtelang04@gmail.com";

        String password =
                "abcdef";

        // Read test data from Excel
        String category =
                ExcelReader.getCellData(
                "NotesData",
                "NormalNote",
                "Category");

        String title =
                ExcelReader.getCellData(
                "NotesData",
                "NormalNote",
                "Title")
                + "_"
                + System.currentTimeMillis();

        String description =
                ExcelReader.getCellData(
                "NotesData",
                "NormalNote",
                "Description");

        // Open UI and login
        DriverFactory
                .getDriver()
                .get(
                "https://practice.expandtesting.com/notes/app");

        loginPage.login(
                email,
                password);

        // Create note from UI
        notesPage.createNote(
                category,
                title,
                description);

        // Search created note
        notesPage.searchNote(
                title);

        // Capture UI values
        String uiTitle =
                notesPage.getDisplayedTitle();

        String uiDescription =
                notesPage.getDisplayedDescription();

      

        // API login
        String token =
                authAPI.login(
                email,
                password);

        Response response =
                notesAPI.getNotes(
                token);

        // Find note from API response
        String apiTitle =
                response.jsonPath()
                .getString(
                "data.find{it.title=='"
                + uiTitle
                + "'}.title");

        String apiDescription =
                response.jsonPath()
                .getString(
                "data.find{it.title=='"
                + uiTitle
                + "'}.description");

        String apiCategory =
                response.jsonPath()
                .getString(
                "data.find{it.title=='"
                + uiTitle
                + "'}.category");

        // Compare UI vs API
        Assert.assertEquals(
                uiTitle,
                apiTitle);

        Assert.assertEquals(
                uiDescription,
                apiDescription);

        Assert.assertEquals(
                category,
                apiCategory);

        System.out.println(
                "UI → API Consistency Passed");
    }
    @Test
    public void validateDeletedNoteDisappearsFromUI() {

        String email =
                "varadvtelang04@gmail.com";

        String password =
                "abcdef";

        String token =
                authAPI.login(
                email,
                password);

        String title =
                "DeleteHybrid_"
                + System.currentTimeMillis();

        // Create note first
        Response createResponse =
                notesAPI.createNote(
                token,
                "Home",
                title,
                "Hybrid delete test");

        String noteId =
                createResponse
                .jsonPath()
                .getString(
                "data.id");

        // Delete note
        notesAPI.deleteNote(
                token,
                noteId);

        // Open UI
        DriverFactory
                .getDriver()
                .get(
                "https://practice.expandtesting.com/notes/app");

        loginPage.login(
                email,
                password);

        // Search deleted note
        notesPage.searchNote(
                title);

        Assert.assertFalse(
                notesPage.isSearchSuccessful(),
                "Deleted note still visible");

        System.out.println(
                "Deleted note removed from UI");
    }
    @AfterMethod
    public void tearDown() {

        DriverFactory.quitDriver();
    }
}