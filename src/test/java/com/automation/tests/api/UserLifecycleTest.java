package com.automation.tests.api;

import com.automation.models.UserRequest;
import com.automation.utils.ApiConfig;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.OffsetDateTime;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class UserLifecycleTest {

    @BeforeClass
    public void setup() {
        ApiConfig.setup();
    }

    @Test
    public void createUser_shouldReturn201WithIdAndCreatedAt() {
        UserRequest request = new UserRequest("Test Buyer", "QA Engineer");

        Response response = given()
                .body(request)
                .when()
                .post("/users");

        assertEquals(response.statusCode(), 201);

        String id = response.jsonPath().getString("id");
        String createdAt = response.jsonPath().getString("createdAt");

        assertNotNull(id, "id should not be null");
        assertFalse(id.isEmpty(), "id should not be empty");
        assertNotNull(createdAt, "createdAt should not be null");
        assertFalse(createdAt.isEmpty(), "createdAt should not be empty");
    }

    @Test
    public void getUser_shouldReturn200WithReqresEmail() {
        Response response = given()
                .when()
                .get("/users/2");

        assertEquals(response.statusCode(), 200);

        String email = response.jsonPath().getString("data.email");
        assertNotNull(email, "email should not be null");
        assertTrue(email.endsWith("@reqres.in"), "email should end with @reqres.in, got: " + email);
    }

    @Test
    public void updateUser_shouldReturn200WithUpdatedAt() {
        String createdAt = OffsetDateTime.now().minusSeconds(5).toString();

        UserRequest request = new UserRequest("Test Buyer", "Senior QA");

        Response response = given()
                .body(request)
                .when()
                .put("/users/2");

        assertEquals(response.statusCode(), 200);

        String updatedAt = response.jsonPath().getString("updatedAt");
        assertNotNull(updatedAt, "updatedAt should not be null");
        assertFalse(updatedAt.isEmpty(), "updatedAt should not be empty");

        OffsetDateTime updatedTime = OffsetDateTime.parse(updatedAt);
        OffsetDateTime createdTime = OffsetDateTime.parse(createdAt);
        assertFalse(updatedTime.isBefore(createdTime),
                "updatedAt should not be older than the request time");
    }

    @Test
    public void registerWithoutPassword_shouldReturn400WithErrorMessage() {
        String body = "{\"email\": \"eve.holt@reqres.in\"}";

        Response response = given()
                .body(body)
                .when()
                .post("/register");

        assertEquals(response.statusCode(), 400);

        String error = response.jsonPath().getString("error");
        assertEquals(error, "Missing password");
    }
}
