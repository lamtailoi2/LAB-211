package model;

import java.io.Serializable;
import java.util.Objects;

public class InsuranceInfo implements Serializable {

    private String id;
    private String licensePlate;
    private String establishedDate;
    private int period;
    private int fees;
    private String owner;

    public InsuranceInfo() {
    }

    public InsuranceInfo(String id, String licensePlate, String establishedDate, int period, String owner) {
        this.id = id.toUpperCase();
        this.licensePlate = licensePlate;
        this.establishedDate = establishedDate;
        this.period = period;
        this.owner = owner;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id.toUpperCase();
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getEstablishedDate() {
        return establishedDate;
    }

    public void setEstablishedDate(String establishedDate) {
        this.establishedDate = establishedDate;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public double getFees() {
        return fees;
    }

    /**
     * Calculates insurance fees based on the vehicle's value.
     * Fee calculation rules:
     * If period is 12 months: fees = 25% of car value
     * If period is 24 months: fees = 20% of car value * 2
     * If period is 36 months: fees = 15% of car value * 3
     *
     * @param carValue The value of the vehicle.
     * @throws IllegalArgumentException if the period is not one of the allowed
     * values.
     */
    public void calculateFees(int carValue) {
        switch (this.period) {
            case 12:
                this.fees = (int) (0.25 * carValue);
                break;
            case 24:
                this.fees = (int) (0.20 * carValue * 2);
                break;
            case 36:
                this.fees = (int) (0.15 * carValue * 3);
                break;
        }
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, licensePlate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        InsuranceInfo other = (InsuranceInfo) obj;
        return Objects.equals(this.id, other.id) && Objects.equals(this.licensePlate, other.getLicensePlate());
    }

    @Override
    public String toString() {
        return "InsuranceInfo{"
                + "id='" + id + '\''
                + ", licensePlate='" + licensePlate + '\''
                + ", establishedDate=" + establishedDate
                + ", period=" + period
                + ", fees=" + fees
                + ", owner='" + owner + '\''
                + '}';
    }
}
