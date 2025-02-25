package tool;

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

    DistrictMapper(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public static DistrictMapper getCode(String code) {
        for (DistrictMapper x : values()) {
            if (x.name().equalsIgnoreCase(code)) {
                return x;
            }
        }
        return null;
    }

    public static String getDistrictName(String code) {
        DistrictMapper d = getCode(code);
        return d != null ? d.name() : "Unknown!";
    }

    public static boolean isValidCode(String licensePlate) {
        try {
            Character code = licensePlate.charAt(2);
            return getCode(code.toString()) != null && Acceptable.isValid(licensePlate, Acceptable.LICENSE_VALID);
        } catch (Exception e) {
            return false;
        }
    }

}
