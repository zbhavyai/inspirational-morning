package io.github.zbhavyai.inspirationalmorning.rest;

import io.github.zbhavyai.inspirationalmorning.service.WeekdayService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/weekday")
public class WeekdayRest {

    private final WeekdayService service;

    @Inject
    public WeekdayRest(final WeekdayService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getWeekDay() {
        return this.service.getWeekDay();
    }
}
