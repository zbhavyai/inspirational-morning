package io.github.zbhavyai.rest;

import io.github.zbhavyai.models.GChatMsgResponse;
import io.github.zbhavyai.service.GreetingsService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/greet")
public class GreetingsRest {

    private final GreetingsService service;

    @Inject
    public GreetingsRest(final GreetingsService service) {
        this.service = service;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<GChatMsgResponse> greet() {
        return this.service.greet();
    }
}
