package util;

import java.time.YearMonth;

public class DateUtil {
    public static YearMonth of(int year, int month) {
        return YearMonth.of(year, month);
    }

    ;
    public final static YearMonth NOW = YearMonth.of(3000, 1);
}
