package util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeUtil {

    public static @Nullable
    LocalDate parseLocalDate(String s) {
        return StringUtils.hasLength(s) ? LocalDate.parse(s) : null;
    }

    public static @Nullable
    LocalTime parseLocalTime(String s) {
        return StringUtils.hasLength(s) ? LocalTime.parse(s) : null;
    }
}
