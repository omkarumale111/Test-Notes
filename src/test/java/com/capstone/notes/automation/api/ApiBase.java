package com.capstone.notes.automation.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class ApiBase {

    protected RequestSpecification requestSpec;

    public ApiBase() {

        requestSpec =
                new RequestSpecBuilder()
                .setBaseUri(
                "https://practice.expandtesting.com/notes/api")
                .setContentType(
                "application/json")
                .build();
    }
}