package io.github.zbhavyai.inspirationalmorning.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

@ApplicationScoped
public class WeekdayService {

    private static final Logger LOGGER = Logger.getLogger(WeekdayService.class.getSimpleName());

    private ZoneId zoneId;

    @Inject
    public WeekdayService(@ConfigProperty(name = "timezone") String tzTimeZone) {
        try {
            this.zoneId = ZoneId.of(tzTimeZone);
        } catch (Exception e) {
            this.zoneId = ZoneId.of("Etc/UTC");
        }
    }

    public String getWeekDay() {
        LOGGER.infof("Getting weekday for zone: \"%s\"", zoneId.getId());

        ZonedDateTime currentZonedDateTime = ZonedDateTime.now(zoneId);

        return currentZonedDateTime
            .getDayOfWeek()
            .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }
}
