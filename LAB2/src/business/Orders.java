package business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Order;
import tool.DateParser;

public class Orders extends HashSet<Order> {

    private static final String HEADER_TABLE = "--------------------------------------------------------------------------------------------------------------\n"
            + "ID                                  | Event Date | Customer ID | Set Menu | Price     | Tables  |     Cost\n"
            + "--------------------------------------------------------------------------------------------------------------";
    private static final String FOOTER_TABLE = "--------------------------------------------------------------------------------------------------------------\n";
    private final String pathFile = "./feast_order_service.dat";
    private static FeastMenus fm = new FeastMenus();
    private Customers cusList;
    private boolean saved;

    public Orders(Customers cusList) {
        super();
        readFromFile();
        this.cusList = cusList;
        this.saved = true;
    }

    public boolean isSaved() {
        return this.saved;
    }

    public void showAll() {
        if (this.isEmpty()) {
            System.out.println("No data in system\n");

        } else {
            DecimalFormat df = new DecimalFormat("#,##0");
            System.out.println(HEADER_TABLE);
            List<Order> sortedList = new ArrayList<>(this);
            Collections.sort(sortedList, new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    LocalDate date1 = DateParser.parse(o1.getEventDate(), "dd/MM/yyyy");
                    LocalDate date2 = DateParser.parse(o2.getEventDate(), "dd/MM/yyyy");
                    return date1.compareTo(date2);
                }
            });

            for (Order o : sortedList) {
                System.out.printf("%-36s| %-10s | %-11s | %-8s | %-8s | %7d | %-8s\n",
                        o.getOrderID(), o.getEventDate(), o.getCustomerCode(), o.getMenuCode(),
                        df.format(o.getUnitPrice()), o.getNumOfTable(), df.format(o.getTotalCost()));
            }
            System.out.println(FOOTER_TABLE);
        }

    }

    public void getOrderDetail(Order o) {
        DecimalFormat df = new DecimalFormat("#,##0");
        System.out.printf(FOOTER_TABLE);
        System.out.printf("Customer order information [Order ID: %s]\n", o.getOrderID());
        System.out.printf(FOOTER_TABLE);
        cusList.getCustomerDetail(o.getCustomerCode());
        System.out.printf(FOOTER_TABLE);
        System.out.println(o);
        System.out.printf(FOOTER_TABLE);
        System.out.printf("Total cost    : %s Vnd\n", df.format(o.getTotalCost()));
        System.out.printf(FOOTER_TABLE);
    }

    public void placeOrder(Order o) {
        if (!isDuplicated(o)) {
            this.add(o);
            this.saved = false;
            getOrderDetail(o);
        } else {
            System.out.println("Duplicate data !");
        }
    }

    public boolean isDuplicated(Order o) {
        return this.contains(o);
    }

    public Order searchByID(String ID) {
        for (Order o : this) {
            if (o.getOrderID().compareTo(ID) == 0) {
                return o;
            }
        }
        return null;
    }

    public boolean isValidID(String ID) {
        return searchByID(ID) != null;
    }

    public void updateOrder(Order newOrder) {
        Order oldOrder = this.searchByID(newOrder.getOrderID());
        System.out.println(oldOrder);
        oldOrder.setCustomerCode(newOrder.getCustomerCode().length() > 0 ? newOrder.getCustomerCode() : oldOrder.getCustomerCode());
        oldOrder.setMenuCode(newOrder.getMenuCode().length() > 0 ? newOrder.getMenuCode() : oldOrder.getMenuCode());
        oldOrder.setNumOfTable(newOrder.getNumOfTable() > 0 ? newOrder.getNumOfTable() : oldOrder.getNumOfTable());
        try {
            LocalDate newDate = DateParser.parse(newOrder.getEventDate(), "dd/MM/yyyy");
            LocalDate oldDate = DateParser.parse(oldOrder.getEventDate(), "dd/MM/yyyy");
            if (newDate.isAfter(oldDate)) {
                oldOrder.setEventDate(newOrder.getEventDate());
            }
        } catch (DateTimeParseException ex) {
        }
        this.saved = false;
        System.out.println("Updated Successfull !");
    }

    public void readFromFile() {
        File f = new File(pathFile);
        if (f.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                while (fis.available() > 0) {
                    Order o = (Order) ois.readObject();
                    if (o != null) {
                        this.add(o);
                    }
                }
                ois.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fis.close();
                } catch (IOException ex) {
                    Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("feast_order_service.dat not Found!");
        }
    }

    public void saveToFile() {
        File f = new File(pathFile);
        if (f.exists()) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for (Order o : this) {
                    oos.writeObject(o);
                }
                oos.close();
                System.out.println("Order data has been successfully saved to “feast_order_service.dat”");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("feast_order_service.dat not Found!");
        }
    }

}
