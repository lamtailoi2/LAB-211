package model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Objects;
import tool.Acceptable;

public class Student implements Serializable {

    private String studentID;
    private String name;
    private String phone;
    private String email;
    private String mountainCode;
    private double tuitionFee;

    public Student() {
    }

    public Student(String studentID, String name, String phone, String email, String mountainCode, double tuitionFee) {
        this.studentID = studentID.toUpperCase();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.mountainCode = mountainCode;
        this.tuitionFee = tuitionFee;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID.toUpperCase();
    }

    public String getName() {
        return name;
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

    public String getMountainCode() {
        return mountainCode;
    }

    public void setMountainCode(String mountainCode) {
        this.mountainCode = mountainCode;
    }

    public double getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(double tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0");
        return String.format("%-12s| %-20s| %-12s| %11s| %-11s", studentID, name, phone, mountainCode, df.format(tuitionFee));
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
        final Student other = (Student) obj;
        return Objects.equals(this.studentID, other.studentID);
    }
}
