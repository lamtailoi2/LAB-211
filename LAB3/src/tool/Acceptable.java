package tool;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public interface Acceptable {

    public final String INT_VALID = "\\d+";
    public final String SEAT_VALID = "^[4-9]|1\\d|2\\d|3[0-6]$";
    public final String NAME_VALID = "^[a-zA-Z_ ]{2,25}$";
    public final String BRAND_VALID = "^[a-zA-Z_ ]{5,12}$";
    public final String PHONE_VALID = "^0\\d{9}$";
    public final String YN_VALID = "^[YyNn]$";
    public final String VALUE_VALID = "^[1-9]\\d{3,}$";
    public final String POS_INT_VALID = "^[1-9]+$";
    public final String LICENSE_VALID = "^5[1-9][a-zA-z][1-9]\\d{5}$";

    /**
     * Validate data
     *
     * @param data the input string to be validated
     * @param pattern the regular expression pattern to validate against
     * @return true if the data matches the pattern, false otherwise
     */
    public static boolean isValid(String data, String pattern) {
        return data.matches(pattern);
    }

    /**
     * Validates whether the provided date string represents a valid future
     * date. The expected date format is "MM/dd/yyyy".
     *
     * @param data the input date string.
     * @return true if the date is correctly formatted and is after the current
     * date, false otherwise.
     */
    public static boolean isValidFutureDate(String data) {
        try {
            // Parse the date using the defined date format.
            LocalDate eventDate = DateParser.parse(data, "MM/dd/yyyy");
            // Check if the event date is in the future compared to the current date.
            return eventDate.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            // If parsing fails, return 
            return false;
        }
    }

}
