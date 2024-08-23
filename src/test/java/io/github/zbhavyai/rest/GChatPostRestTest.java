package io.github.zbhavyai.rest;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
class GChatPostRestTest {

    @Test
    void testPostMessage() {
        given()
                .when()
                .contentType("text/plain")
                .accept("application/json")
                .body("Hello World")
                .post("/api/gchat/post/")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);
        ;
    }
}
