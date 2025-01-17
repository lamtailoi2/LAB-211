package tool;

public interface Acceptable {

    public static final String CUS_ID_VALID = "^[cC|Gg|Kk]\\d{4}$";
    public static final String NAME_VALID = "^[a-zA-Z_ ]{2,25}$";
    public static final String EMAIL_VALID = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$";

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
}
