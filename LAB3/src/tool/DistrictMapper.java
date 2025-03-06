package tool;

/**
 * The DistrictMapper enum provides a mapping between district codes and their
 * corresponding district names. Each enum constant represents a unique
 * district.
 */
public enum DistrictMapper {
    X("TP. Thủ Đức"),
    T("Quận 1"),
    F("Quận 3"),
    C("Quận 4"),
    H("Quận 5"),
    K("Quận 6"),
    Q("Quận 7"),
    L("Quận 8"),
    U("Quận 10"),
    M("Quận 11"),
    G("Quận 12"),
    D("Quận Tân Phú"),
    E("Quận Phú Nhuận"),
    N("Quận Bình Tân"),
    P("Quận Tân Bình"),
    S("Quận Bình Thạnh"),
    V("Quận Gò Vấp"),
    Y("Huyện Hóc Môn"),
    R("Huyện Củ Chi"),
    Z("Huyện Nhà Bè"),
    W("Huyện Cần Giờ"),
    O("Huyện Bình Chánh");

    private final String city;

    /**
     * Constructs a DistrictMapper constant with the specified district name.
     *
     * @param city the city or district name corresponding to this code.
     */
    DistrictMapper(String city) {
        this.city = city;
    }

    /**
     * Returns the city or district name associated with this district code.
     *
     * @return the district name.
     */
    public String getCity() {
        return city;
    }

    /**
     * Retrieves the DistrictMapper constant corresponding to the provided
     * district code.
     *
     * The comparison is case-insensitive.
     *
     * @param code the district code as a string.
     * @return the matching DistrictMapper constant, or null if no match is
     * found.
     */
    public static DistrictMapper getCode(String code) {
        for (DistrictMapper x : values()) {
            if (x.name().equalsIgnoreCase(code)) {
                return x;
            }
        }
        return null;
    }

    /**
     * Retrieves the district name associated with the given district code.
     *
     * @param code the district code as a string.
     * @return the district name if found; otherwise, returns "Unknown!".
     */
    public static String getDistrictName(String code) {
        DistrictMapper d = getCode(code);
        return d != null ? d.getCity() : "Unknown District";
    }

    /**
     * Validates whether the provided license plate contains a valid district
     * code.
     *
     * The method extracts the character at index 2 of the license plate and
     * verifies that it corresponds to a valid district code. It also uses the
     * Acceptable interface to check if the license plate meets the required
     * format.
     *
     * @param licensePlate the license plate string to validate.
     * @return true if the district code is valid and the license plate meets
     * the criteria; false otherwise.
     */
    public static boolean isValidCode(String licensePlate) {
        try {
            Character code = licensePlate.charAt(2);
            return getCode(code.toString()) != null && Acceptable.isValid(licensePlate, Acceptable.LICENSE_VALID);
        } catch (Exception e) {
            return false;
        }
    }
}
