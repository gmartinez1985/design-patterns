package com.germarna.patterns.observer.hexagonalddd.testutils;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtils {
    public static LocalDate toUtcLocalDate(Date d) {
        return d.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
    }

    public static Date tomorrow() {
        final long day = 86_400_000L;
        return new Date(System.currentTimeMillis() + day);
    }

    public static Date dayAfterTomorrow() {
        final long day = 86_400_000L;
        return new Date(System.currentTimeMillis() + 2 * day);
    }
}
