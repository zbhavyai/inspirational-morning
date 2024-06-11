package io.github.zbhavyai.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WeekdayService {

    public String getWeekDay(String zoneID) {
        ZoneId timeZone;

        try {
            timeZone = ZoneId.of(zoneID);
        } catch (Exception e) {
            timeZone = ZoneId.of("America/Edmonton");
        }

        ZonedDateTime currentZonedDateTime = ZonedDateTime.now(timeZone);
        return currentZonedDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }
}
