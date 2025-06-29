package io.github.zbhavyai.client;

import io.github.zbhavyai.models.GChatMsgPostResponse;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.ZonedDateTime;

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

//    public Response parseGChatError(Response response) {
//        // extract the JSON error object from the response which was converted to String
//        // by handleResponse method in VertxRestClient
//        JsonObject json = new JsonObject(response.readEntity(ErrorResponse.class));
//
//        return Response.status(json.getJsonObject("error").getInteger("code"))
//            .entity(ErrorResponse.create(json.getJsonObject("error").getString("message")))
//            .build();
//    }
}
