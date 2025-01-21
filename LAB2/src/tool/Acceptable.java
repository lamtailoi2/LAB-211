package tool;

import business.Customers;

public interface Acceptable {

    public static final String CUS_CODE_VALID = "^[cC|Gg|Kk]\\d{4}$";
    public static final String NAME_VALID = "^[a-zA-Z_ ]{2,25}$";
    public static final String EMAIL_VALID = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$";
    public static final String INT_VALID = "\\d+";
    public static final String PHONE_VALID = "^0\\d{9}$";
    public final String YN_VALID = "^[YyNn]$";

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
