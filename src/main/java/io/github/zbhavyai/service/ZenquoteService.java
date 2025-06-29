package io.github.zbhavyai.service;

import io.github.zbhavyai.client.ZenquoteClient;
import io.github.zbhavyai.models.Zenquote;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ZenquoteService {

    private static final Logger LOGGER = Logger.getLogger(ZenquoteService.class.getSimpleName());

    @RestClient
    private ZenquoteClient zenClient;

    public Uni<Zenquote> getTodayQuote() {
        LOGGER.infof("getTodayQuote");

        return zenClient.getTodayQuote()
            .onItem()
            .transform(this::parseZenquoteResponse)
            .onFailure()
            .recoverWithItem(Zenquote.fallbackQuote());
    }

    public Uni<Zenquote> getRandomQuote() {
        LOGGER.infof("getRandomQuote");
        
        return zenClient.getRandomQuote()
            .onItem()
            .transform(this::parseZenquoteResponse)
            .onFailure()
            .recoverWithItem(Zenquote.fallbackQuote());
    }

    private Zenquote parseZenquoteResponse(JsonArray arr) {
        if (arr == null || arr.isEmpty()) {
            return Zenquote.fallbackQuote();
        }

        JsonObject json = arr.getJsonObject(0);
        if (json == null) {
            return Zenquote.fallbackQuote();
        }

        return new Zenquote(
            json.getString("a"),
            json.getString("q"));
    }
}
