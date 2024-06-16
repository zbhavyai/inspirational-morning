package io.github.zbhavyai.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WeekdayService {

    private static final Logger LOGGER = Logger.getLogger(WeekdayService.class.getSimpleName());

    public String getWeekDay(String zoneID) {
        ZoneId timeZone;

        try {
            timeZone = ZoneId.of(zoneID);
        } catch (Exception e) {
            timeZone = ZoneId.of("America/Edmonton");
        }

        LOGGER.infof("Getting weekday for zone: \"%s\"", timeZone.getId());

        ZonedDateTime currentZonedDateTime = ZonedDateTime.now(timeZone);
        return currentZonedDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }
}
