package io.github.zbhavyai.service;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import io.github.zbhavyai.client.GChatPostParser;
import io.github.zbhavyai.client.VertxRestClient;
import io.github.zbhavyai.models.GChatMsgPostResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
public class GChatPostService {

    private static final Logger LOGGER = Logger.getLogger(GChatPostService.class.getSimpleName());

    private final VertxRestClient restClient;
    private final GChatPostParser parser;
    private final String webhookURL;

    @Inject
    public GChatPostService(
            VertxRestClient restClient,
            GChatPostParser parser,
            @ConfigProperty(name = "gspace.webhook") String webhookURL) {

        this.restClient = restClient;
        this.parser = parser;
        this.webhookURL = webhookURL;
    }

    public Uni<GChatMsgPostResponse> postMessageToGChat(String message) {
        LOGGER.infof("postMessageToGChat: message=\"%s\"", message);

        return this.restClient
                .postRequest(webhookURL, createHeader(), createMessagePayload(message))
                .onItem().transform(r -> this.parser.parseGChatMsgPostResponse(r.readEntity(JsonObject.class)));
    }

    private Map<String, String> createHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", MediaType.APPLICATION_JSON);
        header.put("Accept", MediaType.APPLICATION_JSON);
        return header;
    }

    private JsonObject createMessagePayload(String text) {
        return new JsonObject()
                .put("text", text);
    }
}
