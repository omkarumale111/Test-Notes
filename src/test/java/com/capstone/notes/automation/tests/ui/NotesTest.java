package com.capstone.notes.automation.tests.ui;
import com.capstone.notes.automation.utils.ExcelReader;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;
import com.capstone.notes.automation.hooks.TestListener;
import com.capstone.notes.automation.base.BaseTest;
import com.capstone.notes.automation.config.ConfigReader;
import com.capstone.notes.automation.drivers.DriverFactory;
import com.capstone.notes.automation.pages.LoginPage;
import com.capstone.notes.automation.pages.NotesPage;
@Listeners(TestListener.class)
@Test(singleThreaded = true)
public class NotesTest  {

    LoginPage loginPage;
    NotesPage notesPage;




    @BeforeClass
    public void initializePages()
            throws Exception {

        DriverFactory.initializeDriver();

        DriverFactory.getDriver().get(
                ConfigReader.getProperty(
                "baseUrl"));

        loginPage =
                new LoginPage();

        notesPage =
                new NotesPage();

        loginPage.login(
                "varadvtelang04@gmail.com",
                "abcdef");
    }


    @AfterClass
    public void closeBrowser() {

        DriverFactory.quitDriver();
    }


    @Test(priority = 1)
    public void createNoteTest() {

        System.out.println(
                "[createNoteTest] Starting");

        String category =
                ExcelReader.getCellData(
                "NotesData",
                "NormalNote",
                "Category");

        String title =
                ExcelReader.getCellData(
                "NotesData",
                "NormalNote",
                "Title");

        String description =
                ExcelReader.getCellData(
                "NotesData",
                "NormalNote",
                "Description");

        notesPage.createNote(
                category,
                title,
                description);

        Assert.assertTrue(
                notesPage.isNotePresent());
        
        
        
        
        
        // Wait 2 seconds so browser is in
        // a visible state when screenshot is taken
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//
//        // Intentional failure to test
//        // Allure screenshot functionality
//        Assert.assertTrue(false);

        System.out.println(
                "[createNoteTest] PASSED");
    }


    @Test(priority = 2)
    public void searchNoteTest() {

        System.out.println(
                "[searchNoteTest] Starting");

        String title =
                ExcelReader.getCellData(
                "NotesData",
                "SearchNote",
                "Title");

        notesPage.searchNote(
                title);

        Assert.assertTrue(
                notesPage.isSearchSuccessful());

        System.out.println(
                "[searchNoteTest] PASSED");
    }

    @Test(priority = 3)
    public void editNoteTest() {

        System.out.println(
                "[editNoteTest] Clearing search filter");

        notesPage.searchNote("");

        System.out.println(
                "[editNoteTest] Creating note from Excel");

        String category =
                ExcelReader.getCellData(
                "NotesData",
                "EditNote",
                "Category");

        String title =
                ExcelReader.getCellData(
                "NotesData",
                "EditNote",
                "Title");

        String description =
                ExcelReader.getCellData(
                "NotesData",
                "EditNote",
                "Description");
        String updatedTitle =
                ExcelReader.getCellData(
                "NotesData",
                "EditNote",
                "UpdatedTitle");

        String updatedDescription =
                ExcelReader.getCellData(
                "NotesData",
                "EditNote",
                "UpdatedDescription");

        notesPage.createNote(
                category,
                title,
                description);
        System.out.println(
                "[editNoteTest] Searching for note before edit");

        notesPage.searchNote(
                title);

        System.out.println(
                "[editNoteTest] Note created — starting edit");

        notesPage.editNote(
                updatedTitle,
                updatedDescription);

        System.out.println(
                "[editNoteTest] Edit done — asserting");

        Assert.assertTrue(
                notesPage.isEditSuccessful(
                updatedTitle));

        System.out.println(
                "[editNoteTest] PASSED");
    }


    @Test(priority = 4)
    public void deleteNoteTest() {

        System.out.println(
                "[deleteNoteTest] Starting");

        notesPage.searchNote("");

        String category =
                ExcelReader.getCellData(
                "NotesData",
                "DeleteNote",
                "Category");

        String title =
                ExcelReader.getCellData(
                "NotesData",
                "DeleteNote",
                "Title");

        String description =
                ExcelReader.getCellData(
                "NotesData",
                "DeleteNote",
                "Description");

        // Add timestamp to avoid duplicate notes
        String uniqueTitle =
                title + "_"
                + System.currentTimeMillis();

        System.out.println(
                "[deleteNoteTest] Creating note: "
                + uniqueTitle);

        notesPage.createNote(
                category,
                uniqueTitle,
                description);

        System.out.println(
                "[deleteNoteTest] Note created — deleting");

        notesPage.deleteNote(
                uniqueTitle);

        System.out.println(
                "[deleteNoteTest] Delete done — asserting");

        Assert.assertTrue(
                notesPage.isDeleteSuccessful(
                uniqueTitle));

        System.out.println(
                "[deleteNoteTest] PASSED");
    }
    @Test(priority = 5)
    public void invalidNoteTest() {

        System.out.println(
                "[invalidNoteTest] Starting");

        String category =
                ExcelReader.getCellData(
                "NotesData",
                "InvalidNote",
                "Category");

        String title =
                ExcelReader.getCellData(
                "NotesData",
                "InvalidNote",
                "Title");

        String description =
                ExcelReader.getCellData(
                "NotesData",
                "InvalidNote",
                "Description");

        notesPage.createNote(
                category,
                title,
                description);

        Assert.assertTrue(
                notesPage
                .isTitleValidationDisplayed());

        System.out.println(
                "[invalidNoteTest] PASSED");
    }
    
}