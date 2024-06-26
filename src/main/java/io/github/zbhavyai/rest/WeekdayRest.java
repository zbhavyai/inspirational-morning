package io.github.zbhavyai.rest;

import java.time.ZoneId;

import io.github.zbhavyai.service.WeekdayService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/weekday/")
public class WeekdayRest {

    private final WeekdayService service;

    @Inject
    public WeekdayRest(final WeekdayService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getWeekDay() {
        return this.service.getWeekDay(ZoneId.systemDefault().getId());
    }
}
