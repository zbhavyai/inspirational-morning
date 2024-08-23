package io.github.zbhavyai.rest;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
class ZenquoteRestTest {

    @Test
    void testGetTodaysQuote() {
        given()
                .when().get("/api/zenquote/today/")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void testGetRandomQuote() {
        given()
                .when().get("/api/zenquote/random/")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);
    }
}
