package ru.javawebinar.basejava.util;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static YearMonth of(int year, int month) {
        return YearMonth.of(year, month);
    }

    ;
    public final static YearMonth NOW = YearMonth.of(3000, 1);
    public static String PATTERN = "MMM/yyyy";

    public static String printYearMonth(YearMonth ym) {
        return DateTimeFormatter.ofPattern(PATTERN).format(ym);
    }

    public static YearMonth parseYearMonth(String ym) {
        return YearMonth.parse(ym, DateTimeFormatter.ofPattern(PATTERN));
    }

}
