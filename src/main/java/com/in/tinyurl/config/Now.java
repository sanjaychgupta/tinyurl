package com.in.tinyurl.config;


import java.time.Clock;
import java.time.LocalDateTime;

public class Now {


    static final Clock clock = Clock.systemDefaultZone();

    // You can mock the system time like this
    //    static Clock clock = Clock.fixed(
    //            LocalDateTime.parse("2017-09-27T23:45").toInstant(ZoneOffset.ofHours(9)),
    //            ZoneId.systemDefault());

    public static LocalDateTime localDateTime() {
        return LocalDateTime.now(clock);
    }
}