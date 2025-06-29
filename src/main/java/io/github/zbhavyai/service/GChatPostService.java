package io.github.zbhavyai.service;

import io.github.zbhavyai.client.GChatClient;
import io.github.zbhavyai.models.GChatMsgRequest;
import io.github.zbhavyai.models.GChatMsgResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ServiceUnavailableException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class GChatPostService {

    private static final Logger LOGGER = Logger.getLogger(GChatPostService.class.getSimpleName());

    @RestClient
    private GChatClient gChatClient;

    public Uni<GChatMsgResponse> postMessageToGChat(String message) {
        LOGGER.infof("postMessageToGChat: message=\"%s\"", message);

        return gChatClient
            .postMessage(new GChatMsgRequest(message))
            .onItem()
            .transform(this::parseGChatMsgResponse)
            .onFailure()
            .invoke(t -> LOGGER.errorf("Error while posting message: %s", t.getMessage()))
            .onFailure().transform(t -> new ServiceUnavailableException());
    }

    private GChatMsgResponse parseGChatMsgResponse(JsonObject json) {
        if (json == null || json.isEmpty()) {
            return GChatMsgResponse.fallbackResponse();
        }

        try {
            return new GChatMsgResponse(
                json.getString("name"),
                json.getString("text"),
                json.getJsonObject("thread").getString("name"),
                json.getJsonObject("space").getString("name"));
        } catch (Exception e) {
            return GChatMsgResponse.fallbackResponse();
        }
    }
}
