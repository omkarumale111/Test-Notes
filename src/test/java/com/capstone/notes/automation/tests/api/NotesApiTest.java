package com.capstone.notes.automation.tests.api;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.capstone.notes.automation.api.AuthAPI;
import com.capstone.notes.automation.api.NotesAPI;
import com.capstone.notes.automation.utils.ExcelReader;

import io.restassured.response.Response;

public class NotesApiTest {

    AuthAPI authAPI =
            new AuthAPI();

    NotesAPI notesAPI =
            new NotesAPI();

    @Test
    public void getNotesTest() {

        String token =
                authAPI.login(
                "varadvtelang04@gmail.com",
                "abcdef");

        Response response =
                notesAPI.getNotes(
                token);

        Assert.assertEquals(
                response.getStatusCode(),
                200);

        System.out.println(
                "GET Notes API Passed");
    }
    @Test
    public void responseTimeTest() {

        String token =
                authAPI.login(
                "varadvtelang04@gmail.com",
                "abcdef");

        Response response =
                notesAPI.getNotes(
                token);

        long responseTime =
                response.time();

        Assert.assertTrue(
                responseTime < 2000,
                "Response exceeded 2 seconds");

        System.out.println(
                "Response Time: "
                + responseTime
                + " ms");
    }
    @Test
    public void createNoteApiTest() {

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

        Response response =
                notesAPI.createNote(
                token,
                category,
                title,
                description);

        Assert.assertEquals(
                response.getStatusCode(),
                200);

        System.out.println(
                "Create Note API Passed");
    }
    @Test
    public void deleteNoteApiTest() {

        String token =
                authAPI.login(
                "varadvtelang04@gmail.com",
                "abcdef");

        // Create note first
        String category =
                ExcelReader.getCellData(
                "ApiData",
                "DeleteNoteViaAPI",
                "Category");

        String title =
                ExcelReader.getCellData(
                "ApiData",
                "DeleteNoteViaAPI",
                "Title");

        String description =
                ExcelReader.getCellData(
                "ApiData",
                "DeleteNoteViaAPI",
                "Description");

        Response createResponse =
                notesAPI.createNote(
                token,
                category,
                title,
                description);
        // Extract ID
        String noteId =
                createResponse
                .jsonPath()
                .getString(
                "data.id");

        System.out.println(
                "Created Note ID: "
                + noteId);

        // Delete note
        Response deleteResponse =
                notesAPI.deleteNote(
                token,
                noteId);

        Assert.assertEquals(
                deleteResponse.getStatusCode(),
                200);

        System.out.println(
                "Delete Note API Passed");
    }
    @Test
    public void invalidCreateNoteApiTest() {

        String token =
                authAPI.login(
                "varadvtelang04@gmail.com",
                "abcdef");

        String category =
                ExcelReader.getCellData(
                "APIData",
                "InvalidCreateNoteAPI",
                "Category");

        String title =
                ExcelReader.getCellData(
                "APIData",
                "InvalidCreateNoteAPI",
                "Title");

        String description =
                ExcelReader.getCellData(
                "APIData",
                "InvalidCreateNoteAPI",
                "Description");

        Response response =
                notesAPI.createNote(
                token,
                category,
                title,
                description);

        Assert.assertTrue(
                response.getStatusCode() >= 400,
                "Invalid request unexpectedly passed");

        System.out.println(
                "Invalid API validation passed");
    }
}