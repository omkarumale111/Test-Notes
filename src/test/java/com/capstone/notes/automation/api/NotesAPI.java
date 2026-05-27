package com.capstone.notes.automation.api;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Allure;
import io.restassured.response.Response;

public class NotesAPI
        extends ApiBase {

    public Response getNotes(
            String token) {

        Response response =
                given()
                .spec(requestSpec)
                .header(
                "x-auth-token",
                token)
                .when()
                .get("/notes");

        Allure.addAttachment(
                "Get Notes Response",
                response.asPrettyString());

        Allure.addAttachment(
                "Response Time",
                response.time() + " ms");

        return response;
    }
    public Response createNote(
            String token,
            String category,
            String title,
            String description) {

        String requestBody =
                "{"
                + "\"category\":\"" + category + "\","
                + "\"title\":\"" + title + "\","
                + "\"description\":\"" + description + "\""
                + "}";

        Allure.addAttachment(
                "Create Note Request",
                requestBody);

        Response response =
                given()
                .spec(requestSpec)
                .header(
                "x-auth-token",
                token)
                .body(requestBody)
                .when()
                .post("/notes");

        Allure.addAttachment(
                "Create Note Response",
                response.asPrettyString());

        return response;
    }
    public Response deleteNote(
            String token,
            String noteId) {

        Response response =
                given()
                .spec(requestSpec)
                .header(
                "x-auth-token",
                token)
                .when()
                .delete(
                "/notes/" + noteId);

        Allure.addAttachment(
                "Delete Note Response",
                response.asPrettyString());

        return response;
    }
}