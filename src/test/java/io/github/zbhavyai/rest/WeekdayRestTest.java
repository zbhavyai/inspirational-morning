package io.github.zbhavyai.rest;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
class WeekdayRestTest {

    @Test
    void testGetWeekday() {
        given()
                .when().get("/api/weekday/")
                .then()
                .statusCode(200)
                .contentType(MediaType.TEXT_PLAIN);
    }
}
