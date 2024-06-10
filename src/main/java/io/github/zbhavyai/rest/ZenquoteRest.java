package io.github.zbhavyai.rest;

import io.github.zbhavyai.service.ZenquoteService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/zenquote/")
public class ZenquoteRest {

    private final ZenquoteService service;

    @Inject
    public ZenquoteRest(final ZenquoteService service) {
        this.service = service;
    }

    @Path("today/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<?> getTodaysQuote() {
        return this.service.getTodaysQuote();
    }

    @Path("random/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<?> getRandomQuote() {
        return this.service.getRandomQuote();
    }
}
