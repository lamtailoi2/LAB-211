package model;

public class Order {

    private String customerCode;
    private String menuCode;
    private int numOfTable;
    private String eventDate;

    public Order() {
    }

    public Order(String customerCode, String menuCode, int numOfTable, String eventDate) {
        this.customerCode = customerCode;
        this.menuCode = menuCode;
        this.numOfTable = numOfTable;
        this.eventDate = eventDate;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public int getNumOfTable() {
        return numOfTable;
    }

    public void setNumOfTable(int numOfTable) {
        this.numOfTable = numOfTable;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

//    @Override
//    public String toString() {
//        
//    }

}
