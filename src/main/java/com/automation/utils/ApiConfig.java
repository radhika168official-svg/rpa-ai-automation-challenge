package com.automation.utils;

import com.automation.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiConfig {

    private static boolean initialized = false;

    public static synchronized void setup() {
        if (!initialized) {
            RestAssured.baseURI = ConfigReader.get("api.base.url");
            RestAssured.requestSpecification = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .header("x-api-key", ConfigReader.get("api.key"));
            initialized = true;
        }
    }
}
