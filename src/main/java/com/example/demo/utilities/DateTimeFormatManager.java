package com.example.demo.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeFormatManager {

    public static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    private DateTimeFormatManager() {};

    public static LocalDateTime getLocalDateTimeFormat(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        return LocalDateTime.parse(dateTime, formatter);
    }
}
