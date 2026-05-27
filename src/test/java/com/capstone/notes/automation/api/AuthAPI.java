package com.capstone.notes.automation.api;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Allure;
import io.restassured.response.Response;

public class AuthAPI
        extends ApiBase {

    public String login(
            String email,
            String password) {

        String requestBody =
                "{"
                + "\"email\":\"" + email + "\","
                + "\"password\":\"" + password + "\""
                + "}";

        Allure.addAttachment(
                "Login Request",
                requestBody);

        Response response =
                given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/users/login");

        Allure.addAttachment(
                "Login Response",
                response.asPrettyString());

        return response
                .jsonPath()
                .getString("data.token");
    }
}