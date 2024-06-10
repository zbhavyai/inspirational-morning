package io.github.zbhavyai.client;

import java.time.ZonedDateTime;

import io.github.zbhavyai.models.GChatMsgPostResponse;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GChatPostParser {

    public GChatMsgPostResponse parseGChatMsgPostResponse(JsonObject json) {
        GChatMsgPostResponse emptyResponse = new GChatMsgPostResponse(
                "",
                "",
                ZonedDateTime.now(),
                "",
                "");

        if (json == null || json.isEmpty()) {
            return emptyResponse;
        }

        try {
            return new GChatMsgPostResponse(
                    json.getJsonObject("sender").getString("displayName"),
                    json.getJsonObject("space").getString("displayName"),
                    ZonedDateTime.parse(json.getString("createTime")),
                    json.getString("formattedText"),
                    json.getJsonObject("thread").getJsonObject("retentionSettings").getString("state"));
        } catch (Exception e) {
            return emptyResponse;
        }
    }
}
