package com.example.demo.services.reservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatManager {

    public static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    public static LocalDateTime getLocalDateTimeFormat(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        return LocalDateTime.parse(dateTime, formatter);
    }

    ;
}
