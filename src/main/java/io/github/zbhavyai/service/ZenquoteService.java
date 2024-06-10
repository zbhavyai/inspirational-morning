package io.github.zbhavyai.service;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import io.github.zbhavyai.client.VertxRestClient;
import io.github.zbhavyai.client.ZenquoteParser;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
public class ZenquoteService {

    private static final Logger LOGGER = Logger.getLogger(ZenquoteService.class.getSimpleName());

    private final VertxRestClient restClient;
    private final ZenquoteParser parser;

    private final String zqTodayEndpoint;
    private final String zqRandomEndpoint;

    @Inject
    public ZenquoteService(
            VertxRestClient restClient,
            ZenquoteParser parser,
            @ConfigProperty(name = "zenquotes.today") String todayEndpoint,
            @ConfigProperty(name = "zenquotes.random") String randomEndpoint) {

        this.zqTodayEndpoint = todayEndpoint;
        this.zqRandomEndpoint = randomEndpoint;
        this.restClient = restClient;
        this.parser = parser;
    }

    public Uni<?> getTodaysQuote() {
        LOGGER.infof("getTodaysQuote");

        return this.restClient
                .getRequest(this.zqTodayEndpoint, createHeader())
                .onItem().transform(r -> this.parser.parseZenquoteResponse(r.readEntity(JsonArray.class)));
    }

    public Uni<?> getRandomQuote() {
        LOGGER.infof("getRandomQuote");

        return this.restClient
                .getRequest(this.zqRandomEndpoint, createHeader())
                .onItem().transform(r -> this.parser.parseZenquoteResponse(r.readEntity(JsonArray.class)));
    }

    private Map<String, String> createHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("Accept", MediaType.APPLICATION_JSON);
        return header;
    }
}
