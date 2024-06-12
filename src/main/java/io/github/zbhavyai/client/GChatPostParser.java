package io.github.zbhavyai.client;

import java.time.ZonedDateTime;

import io.github.zbhavyai.models.ErrorResponse;
import io.github.zbhavyai.models.GChatMsgPostResponse;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

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

    public Response parseGChatError(Response response) {
        // extract the JSON error object from the response which was converted to String
        // by handleResponse method in VertxRestClient
        JsonObject json = new JsonObject(response.readEntity(ErrorResponse.class).error());

        return Response.status(json.getJsonObject("error").getInteger("code"))
                .entity(new ErrorResponse(
                        Status.fromStatusCode(json.getJsonObject("error").getInteger("code")),
                        json.getJsonObject("error").getString("message")))
                .build();
    }
}
