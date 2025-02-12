package model;

import business.FeastMenus;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.UUID;

public class Order implements Serializable {
    
    private String orderID;
    private String customerCode;
    private String menuCode;
    private int numOfTables;
    private String eventDate;
    private static FeastMenus fm = new FeastMenus();
    
    public Order() {
        this.orderID = UUID.randomUUID().toString();
    }
    
    public Order(String orderID) {
        this.orderID = orderID;
    }
    
    public Order(String customerCode, String menuCode, int numOfTables, String eventDate) {
        this();
        this.customerCode = customerCode;
        this.menuCode = menuCode;
        this.numOfTables = numOfTables;
        this.eventDate = eventDate;
    }
    
    public String getCustomerCode() {
        return customerCode;
    }
    
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode.toUpperCase();
    }
    
    public String getMenuCode() {
        return menuCode;
    }
    
    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
    
    public int getNumOfTable() {
        return numOfTables;
    }
    
    public void setNumOfTable(int numOfTables) {
        this.numOfTables = numOfTables;
    }
    
    public String getEventDate() {
        return eventDate;
    }
    
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
        
    }
    
    public String getOrderID() {
        return orderID;
    }
    
    public int getUnitPrice() {
        FeastMenu feastMenu = fm.findFeastMenuByCode(menuCode);
        return feastMenu.getPrice();
    }
    
    public double getTotalCost() {
        FeastMenu feastMenu = fm.findFeastMenuByCode(menuCode);
        return fm.isValidCode(menuCode) ? feastMenu.getPrice() * numOfTables : 0;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(customerCode, menuCode, eventDate);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        return customerCode.equals(other.customerCode)
                && menuCode.equals(other.menuCode)
                && eventDate.equals(other.eventDate)
                && orderID.equals(other.orderID);
    }
    
    @Override
    public String toString() {
        FeastMenu menu = fm.findFeastMenuByCode(menuCode);
        DecimalFormat df = new DecimalFormat("#,##0");
        StringBuilder sb = new StringBuilder();
        
        String[] ingredients = menu.getIngredient().split("#");
        for (String ingredient : ingredients) {
            String cleanedIngredient = ingredient.trim().replaceAll("\"", "");
            sb.append(cleanedIngredient).append("\n");
        }
        return String.format(
                "Customer Code    : %s\n"
                + "Code of Set Menu : %s\n"
                + "Set menu name    : %s\n"
                + "Event Date       : %s\n"
                + "Number of tables : %d\n"
                + "Price            : %s VND\n"
                + "Ingredients:\n%s",
                customerCode,
                getMenuCode(),
                menu.getName(),
                eventDate,
                numOfTables,
                df.format(getUnitPrice()),
                sb.toString().trim()
        );
    }
}
