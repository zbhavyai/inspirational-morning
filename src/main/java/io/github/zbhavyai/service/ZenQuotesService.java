package io.github.zbhavyai.service;

import io.github.zbhavyai.client.ZenQuotesClient;
import io.github.zbhavyai.models.ZenQuote;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ZenQuotesService {

    private static final Logger LOGGER = Logger.getLogger(ZenQuotesService.class.getSimpleName());

    @RestClient
    private ZenQuotesClient zenQuotesClient;

    public Uni<ZenQuote> getTodayQuote() {
        LOGGER.infof("getTodayQuote");

        return zenQuotesClient.getTodayQuote()
            .onItem()
            .transform(this::parseZenQuotesResponse)
            .onFailure()
            .recoverWithItem(ZenQuote.fallbackQuote());
    }

    public Uni<ZenQuote> getRandomQuote() {
        LOGGER.infof("getRandomQuote");

        return zenQuotesClient.getRandomQuote()
            .onItem()
            .transform(this::parseZenQuotesResponse)
            .onFailure()
            .recoverWithItem(ZenQuote.fallbackQuote());
    }

    private ZenQuote parseZenQuotesResponse(JsonArray arr) {
        if (arr == null || arr.isEmpty()) {
            return ZenQuote.fallbackQuote();
        }

        JsonObject json = arr.getJsonObject(0);
        if (json == null) {
            return ZenQuote.fallbackQuote();
        }

        return new ZenQuote(
            json.getString("a"),
            json.getString("q"));
    }
}
