package tool;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface DateParser {

    public static LocalDate parse(String data, String pattern) {
        return LocalDate.parse(data, DateTimeFormatter.ofPattern(pattern));
    }
}
