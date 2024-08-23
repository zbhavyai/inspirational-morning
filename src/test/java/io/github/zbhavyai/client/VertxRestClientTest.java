package io.github.zbhavyai.client;

import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import io.github.zbhavyai.utils.JSONPrinter;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.Vertx;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
class VertxRestClientTest {

    VertxRestClient restClient = new VertxRestClient(Vertx.vertx(), 4);
    private static final Logger LOGGER = Logger.getLogger(VertxRestClientTest.class.getSimpleName());

    @Test
    void testGetRequest() {
        LOGGER.info(
                JSONPrinter.prettyPrint(
                        restClient.getRequest(
                                "https://asdasdsd.io/api/todsay",
                                createHeader(),
                                JsonArray.class).await().indefinitely()));
    }

    @Test
    void testGetRequest2() {
        LOGGER.info(
                JSONPrinter.prettyPrint(
                        restClient.getRequest(
                                "https://api.powerbi.com/v1.0/myorg/groups/52ab32cc-1580-4898-9136-ae407522ed53/dashboards",
                                createHeader(),
                                JsonObject.class).await().indefinitely()));
    }

    private Map<String, String> createHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("Accept", MediaType.APPLICATION_JSON);
        return header;
    }
}
