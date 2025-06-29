package io.github.zbhavyai.client;

import io.quarkus.rest.client.reactive.ClientQueryParam;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "zenquotes-api")
public interface ZenQuotesClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ClientQueryParam(name = "api", value = "random")
    Uni<JsonArray> getRandomQuote();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ClientQueryParam(name = "api", value = "today")
    Uni<JsonArray> getTodayQuote();
}

