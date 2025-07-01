package io.github.zbhavyai.inspirationalmorning.rest;

import io.github.zbhavyai.inspirationalmorning.models.ZenQuote;
import io.github.zbhavyai.inspirationalmorning.service.ZenQuotesService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/zenquote")
public class ZenQuotesRest {

    private final ZenQuotesService service;

    @Inject
    public ZenQuotesRest(final ZenQuotesService service) {
        this.service = service;
    }

    @Path("/today")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<ZenQuote> getTodayQuote() {
        return this.service.getTodayQuote();
    }

    @Path("/random")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<ZenQuote> getRandomQuote() {
        return this.service.getRandomQuote();
    }
}
