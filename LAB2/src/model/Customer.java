package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Customer implements Serializable {

    private String code;
    private String name;
    private String phone;
    private String email;

    public Customer() {
    }

    public Customer(String code, String name, String phone, String email) {
        this.code = code.toUpperCase();
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        String[] nameSplit = name.split(" ");
        return nameSplit[nameSplit.length - 1];
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        String[] nameSplit = name.split(" ");
        String formattedName;
        if (nameSplit.length > 1) {
            formattedName = getFirstName() + ", " + String.join(" ", Arrays.copyOf(nameSplit, nameSplit.length - 1));
        } else {
            formattedName = name; 
        }
        return String.format("%-8s| %-22s| %-12s| %-19s", code, formattedName, phone, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        return Objects.equals(this.code, other.code);
    }
}
