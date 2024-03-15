package no.halvorteigen.powerassetoptimizer.utils;

import java.time.DateTimeException;
import java.time.YearMonth;

public class DateUtils {

    private DateUtils() {
        // Hide default constructor
    }

    public static boolean isValidDate(int year, int month, int day) {
        try {
            return YearMonth.of(year, month).isValidDay(day);
        } catch (DateTimeException exception) {
            return false;
        }
    }
}
