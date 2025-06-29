package io.github.zbhavyai.rest;

import io.github.zbhavyai.models.GChatMsgResponse;
import io.github.zbhavyai.service.GChatPostService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/gchat")
public class GChatPostRest {

    private final GChatPostService service;

    @Inject
    public GChatPostRest(final GChatPostService service) {
        this.service = service;
    }

    @Path("/post")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<GChatMsgResponse> postMessageToGChat(final String message) {
        return this.service.postMessageToGChat(message);
    }
}
