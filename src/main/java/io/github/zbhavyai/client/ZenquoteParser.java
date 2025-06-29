package io.github.zbhavyai.client;

import io.github.zbhavyai.models.Zenquote;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Singleton;

@Singleton
public class ZenquoteParser {

    public Zenquote parseZenquoteResponse(JsonArray arr) {
        Zenquote emptyQuote = new Zenquote("", "", "");

        if (arr == null || arr.isEmpty()) {
            return emptyQuote;
        }

        JsonObject json = arr.getJsonObject(0);
        if (json == null) {
            return emptyQuote;
        }

        return new Zenquote(
            json.getString("a"),
            json.getString("q"),
            json.getString("h"));
    }
}
