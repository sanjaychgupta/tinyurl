package com.in.tinyurl.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentDateTime {

    private final LocalDateTime value;
    public static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    private CurrentDateTime(LocalDateTime value) {
        this.value = value;
    }

    public static CurrentDateTime now() {
        return new CurrentDateTime(Now.localDateTime());
    }

    public static CurrentDateTime from(LocalDateTime value) {
        return new CurrentDateTime(value);
    }

    public LocalDateTime value() {
        return value;
    }
}