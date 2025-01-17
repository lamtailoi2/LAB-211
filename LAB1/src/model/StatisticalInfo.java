package model;

import java.text.DecimalFormat;

public class StatisticalInfo {

    private String mountainCode;
    private int numOfStudent;
    private double totalCost;

    public StatisticalInfo() {
    }

    public StatisticalInfo(String mountainCode, int numOfStudent, double totalCost) {
        this.mountainCode = mountainCode;
        this.numOfStudent = numOfStudent;
        this.totalCost = totalCost;
    }

    public String getMountainCode() {
        return mountainCode;
    }

    public void setMountainCode(String mountainCode) {
        this.mountainCode = mountainCode;
    }

    public int getNumOfStudent() {
        return numOfStudent;
    }

    public void setNumOfStudent(int numOfStudent) {
        this.numOfStudent = numOfStudent;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0");
        return String.format("%-20s| %-23d| %-12s", mountainCode, numOfStudent, df.format(totalCost));
    }

}
