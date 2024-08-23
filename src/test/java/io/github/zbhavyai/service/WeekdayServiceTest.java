package io.github.zbhavyai.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
class WeekdayServiceTest {

    @InjectMocks
    WeekdayService weekdayService;

    private static final String DEFAULT_TIMEZONE = "America/Edmonton";
    private static final String VALID_ZONE = "Europe/London";
    private static final String INVALID_ZONE = "Invalid/Zone";

    private ZonedDateTime fixedTime;

    @BeforeEach
    void setUp() {
        // Use a fixed date for deterministic results
        fixedTime = ZonedDateTime.of(2024, 8, 22, 12, 0, 0, 0, ZoneId.of(VALID_ZONE));
    }

    @Test
    void testGetWeekDayWithValidZone() {
        try (MockedStatic<ZonedDateTime> mockedZonedDateTime = mockStatic(ZonedDateTime.class)) {
            mockedZonedDateTime.when(() -> ZonedDateTime.now(ZoneId.of(VALID_ZONE))).thenReturn(fixedTime);

            String dayOfWeek = weekdayService.getWeekDay(VALID_ZONE);
            String expectedDay = fixedTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            assertEquals(expectedDay, dayOfWeek);
        }
    }

    @Test
    void testGetWeekDayWithInvalidZone() {
        try (MockedStatic<ZonedDateTime> mockedZonedDateTime = mockStatic(ZonedDateTime.class)) {
            mockedZonedDateTime.when(() -> ZonedDateTime.now(ZoneId.of(DEFAULT_TIMEZONE))).thenReturn(fixedTime);

            String dayOfWeek = weekdayService.getWeekDay(INVALID_ZONE);
            String expectedDay = fixedTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            assertEquals(expectedDay, dayOfWeek);
        }
    }

    @Test
    void testGetWeekDayWithDefaultZone() {
        try (MockedStatic<ZonedDateTime> mockedZonedDateTime = mockStatic(ZonedDateTime.class)) {
            mockedZonedDateTime.when(() -> ZonedDateTime.now(ZoneId.of(DEFAULT_TIMEZONE))).thenReturn(fixedTime);

            String dayOfWeek = weekdayService.getWeekDay(DEFAULT_TIMEZONE);
            String expectedDay = fixedTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            assertEquals(expectedDay, dayOfWeek);
        }
    }
}
