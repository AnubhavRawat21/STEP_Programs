package com.gdb.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * DateUtils
 */
public class DateUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static int calculateAge(LocalDate dob) {
        if (dob == null)
            return 0;
        return Period.between(dob, LocalDate.now()).getYears();
    }

    public static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }
}
