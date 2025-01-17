package tool;

public interface Acceptable {

    public final String STU_ID_VALID = "^[Qq|Hh|Ss|Cc][Ee]\\d{6}$";
    public final String CAMPUS_VALID = "^[Qq|Hh|Ss|Cc][Ee]{2}$";
    public final String NAME_VALID = "^.{2,20}$";
    public final String INT_VALID = "\\d+";
    public final String DOUBLE_VALID = "\\d+\\.?\\d*$";
    public final String EMAIL_VALID = "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$";
    public final String PHONE_VALID = "^0\\d{9}$";
    public final String VNPT_VALID = "^(081|082|083|084|085|088|091)\\d{7}$";
    public final String VIETTEL_VALID = "^(086|096|097|098|032|033|034|035|036|037|038|039)\\d{7}$";
    public final String DELETE_VALID = "^[YyNn]$";

    public static boolean isValid(String data, String pattern) {
        return data.matches(pattern);
    }
}
