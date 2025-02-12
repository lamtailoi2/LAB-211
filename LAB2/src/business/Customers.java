package business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;

public class Customers extends ArrayList<Customer> {

    private final String pathFile = "./customers.dat";
    private final String TABLE_HEADER = "----------------------------------------------------------------------- \n"
            + "CODE    | Customer Name         | Phone       | Email              \n"
            + "----------------------------------------------------------------------- ";
    private final String TABLE_FOOTER = "----------------------------------------------------------------------- \n";
    private boolean saved;

    public Customers() {
        super();
        readFromFile();
        this.saved = true;
    }

    public boolean isSaved() {
        return saved;
    }

    public void showAll() {
        showAll(this);
    }

    public void showAll(List<Customer> list) {
        if (this.isEmpty()) {
            System.out.println("The customer list is empty!");
        } else {
            System.out.println(TABLE_HEADER);
            Collections.sort(list, new Comparator<Customer>() {
                @Override
                public int compare(Customer c1, Customer c2) {
                    return c1.getFirstName().compareToIgnoreCase(c2.getFirstName());
                }
            });
            for (Customer i : list) {
                System.out.println(i);
            }
        }
        System.out.println(TABLE_FOOTER);
    }

    public void addCustomer(Customer x) {
        this.add(x);
        this.saved = false;
        System.out.println("Added Successfully!\n");
    }

    public Customer searchByCode(String code) {
        Customer x = new Customer();
        for (Customer i : this) {
            if (i.getCode().equalsIgnoreCase(code.trim())) {

                return i;
            }
        }
        return null;
    }

    public void getCustomerDetail(String code) {
        Customer x = searchByCode(code);
        if (x == null) {
            System.out.println("Student does not exist!");
        } else {
            System.out.printf(
                    "Code           : %s\n"
                    + "Customer Name  : %s\n"
                    + "Phone number   : %s \n"
                    + "Email          : %s\n", x.getCode(), x.getName(), x.getPhone(), x.getEmail());
        }

    }

    public void searchByName(String name) {
        List<Customer> list = new ArrayList();
        for (Customer i : this) {
            if (i.getName().toLowerCase().endsWith(name.toLowerCase())) {
                list.add(i);
            }
        }
        if (list.isEmpty()) {
            System.out.println("No one matches the search criteria!");
        } else {
            showAll(list);
        }
    }

    public boolean isExisted(String code) {
        return searchByCode(code) != null;
    }

    public void updateCustomer(Customer x) {
        Customer existingCustomer = this.searchByCode(x.getCode());
        existingCustomer.setName(x.getName().length() > 0 ? x.getName() : existingCustomer.getName());
        existingCustomer.setEmail(x.getEmail().length() > 0 ? x.getEmail() : existingCustomer.getEmail());
        existingCustomer.setPhone(x.getPhone().length() > 0 ? x.getPhone() : existingCustomer.getPhone());
        this.saved = false;
        System.out.println("Updated Successfully!\n");
    }

    public void readFromFile() {
        File f = new File(pathFile);
        if (f.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                while (fis.available() > 0) {
                    Customer x = (Customer) ois.readObject();
                    if (x != null) {
                        this.add(x);
                    }
                }
                ois.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Customers.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(Customers.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Customers.class
                        .getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fis.close();

                } catch (IOException ex) {
                    Logger.getLogger(Customers.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("File not found\n");
        }
    }

    public void saveToFile() {
        FileOutputStream fos = null;
        try {
            File f = new File(pathFile);
            fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Customer i : this) {
                oos.writeObject(i);
            }
            oos.close();
            this.saved = true;
            System.out.println("Customer data has been successfully saved to “customers.dat”");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Customers.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Customers.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();

            } catch (IOException ex) {
                Logger.getLogger(Customers.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
