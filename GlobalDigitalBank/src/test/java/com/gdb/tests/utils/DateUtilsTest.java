package com.gdb.tests.utils;

import com.gdb.utils.DateUtils;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    void TestCalculateAge_ValidPastDate() {
        LocalDate dob = LocalDate.now().minusYears(20);
        assertEquals(20, DateUtils.calculateAge(dob));
    }

    @Test
    void TestCalculateAge_BirthdayToday() {
        // If someone was born exactly 18 years ago today, they are 18
        LocalDate dob = LocalDate.now().minusYears(18);
        assertEquals(18, DateUtils.calculateAge(dob));
    }

    @Test
    void TestCalculateAge_BirthdayTomorrow() {
        // If someone was born 18 years ago tomorrow, they are still 17
        LocalDate dob = LocalDate.now().minusYears(18).plusDays(1);
        assertEquals(17, DateUtils.calculateAge(dob));
    }

    @Test
    void TestCalculateAge_NullDate() {
        assertEquals(0, DateUtils.calculateAge(null));
    }

    @Test
    void TestCalculateAge_LeapYear() {
        // Born on Feb 29, 2004 (Leap Year). On Feb 28, 2024, they are 19?
        // Actually java.time.Period handles this.
        LocalDate dob = LocalDate.of(2004, 2, 29);
        LocalDate now = LocalDate.of(2024, 2, 28);
        assertEquals(19, Period.between(dob, now).getYears());
    }

    @Test
    void TestCalculateAge_FutureDate() {
        LocalDate dob = LocalDate.now().plusYears(5);
        assertTrue(DateUtils.calculateAge(dob) < 0);
    }

    @Test
    void TestParseDate_ValidFormat() {
        String dateStr = "15/08/1947";
        LocalDate date = DateUtils.parseDate(dateStr);
        assertNotNull(date);
        assertEquals(1947, date.getYear());
        assertEquals(8, date.getMonthValue());
        assertEquals(15, date.getDayOfMonth());
    }

    @Test
    void TestParseDate_InvalidFormat() {
        String dateStr = "1947-08-15";
        assertThrows(DateTimeParseException.class, () -> {
            DateUtils.parseDate(dateStr);
        });
    }

    @Test
    void TestParseDate_InvalidDate() {
        String dateStr = "32/01/2020";
        assertThrows(DateTimeParseException.class, () -> {
            DateUtils.parseDate(dateStr);
        });
    }
}
