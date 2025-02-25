package model;

import java.io.Serializable;
import java.util.Objects;
import tool.DistrictMapper;

public class Car implements Serializable {

    private String licensePlate;
    private String owner;
    private String phone;
    private String brand;
    private int value;
    private String regDate;
    private String regLocation;
    private int numberOfSeat;

    public Car() {

    }

    public Car(String licensePlate, String owner, String phone, String brand, int value, String regDate, int numberOfSeat) {
        this.licensePlate = licensePlate.toUpperCase();
        this.owner = owner;
        this.phone = phone;
        this.brand = brand.toUpperCase();
        this.value = value;
        this.regDate = regDate;
        setLicensePlate(licensePlate);
        this.numberOfSeat = numberOfSeat;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate.toUpperCase();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand.toUpperCase();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getRegLocation() {
        return regLocation;
    }

    public void setRegLocation(String regLocation) {
        Character code = licensePlate.charAt(2);
        this.regLocation = DistrictMapper.getDistrictName(code.toString());
    }

    public int getNumberOfSeat() {
        return numberOfSeat;
    }

    public void setNumberOfSeat(int numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.licensePlate);
        return hash;
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
        final Car other = (Car) obj;
        return Objects.equals(this.licensePlate, other.licensePlate);
    }

    @Override
    public String toString() {
        return String.format(
                "-----------------------------------------------------%n"
                + "License plate    : %s%n"
                + "Owner            : %s%n"
                + "Phone            : %s%n"
                + "Car brand        : %s%n"
                + "Value of vehicle : %,d%n"
                + "Number of seats  : %d%n"
                + "Registration date: %s%n"
                + "-----------------------------------------------------",
                licensePlate,
                owner,
                phone,
                brand,
                value,
                numberOfSeat,
                regDate
        );
    }

}
