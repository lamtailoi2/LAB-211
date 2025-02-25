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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Car;
import model.InsuranceInfo;
import tool.DateParser;

public class Insurances extends HashMap<String, InsuranceInfo> {

    private Cars cars;
    private final String TABLE_HEADER = String.format(
            "%-4s %-12s %-15s %-15s %-15s %-18s %-14s\n",
            "No.",
            "Insurance Id",
            "Established",
            "License plate",
            "Customer",
            "Insurance period",
            "Insurance fees"
    );
    private final String TABLE_HEADER_2 = String.format("%-4s %-12s %-15s %-15s %-15s %-10s %-12s\n",
            "No.", "LicensePlate", "RegDate", "Owner", "Brand", "Seats", "Value");
    private final String pathFile = "./insurances.dat";
    private boolean saved;

    public Insurances(Cars cars) {
        super();
        loadFromFile();
        this.cars = cars;
        this.saved = true;
    }
    
    public boolean isSaved() {
        return this.saved;
    }

    public void addInsurance(InsuranceInfo x) {
        Car car = cars.findCarByLicensePlate(x.getLicensePlate());
        x.calculateFees(car.getValue());
        this.put(x.getId(), x);
        this.saved = false;
    }

    public List<InsuranceInfo> findByYear(int year) {
        List<InsuranceInfo> list = new ArrayList<>();

        for (InsuranceInfo info : this.values()) {
            String dateStr = info.getEstablishedDate();
            LocalDate date = DateParser.parse(dateStr, "MM/dd/yyyy");
            if (date.getYear() == year) {
                list.add(info);
            }
        }

        Collections.sort(list, new Comparator<InsuranceInfo>() {
            @Override
            public int compare(InsuranceInfo a, InsuranceInfo b) {
                LocalDate dateA = DateParser.parse(a.getEstablishedDate(), "MM/dd/yyyy");
                LocalDate dateB = DateParser.parse(b.getEstablishedDate(), "MM/dd/yyyy");
                return dateA.compareTo(dateB);
            }
        });
        return list;
    }

    public InsuranceInfo findByLicensePlate(String licensePlate) {
        Iterator itr = this.values().iterator();
        InsuranceInfo ins = null;
        while (itr.hasNext() && ins == null) {
            InsuranceInfo temp = (InsuranceInfo) itr.next();
            if (temp.getLicensePlate().equalsIgnoreCase(licensePlate)) {
                ins = temp;
            }
        }
        return ins;
    }

    public List<Car> findUninsured() {
        List<Car> list = new ArrayList<>();

        for (String key : cars.keySet()) {
            if (this.findByLicensePlate(key) == null) {
                list.add(cars.get(key));
            }

        }
        Collections.sort(list, new Comparator<Car>() {
            @Override
            public int compare(Car t, Car t1) {
                return Integer.compare(t1.getNumberOfSeat(), t.getNumberOfSeat());
            }

        });
        return list;
    }

    public void showAll(List<InsuranceInfo> list, int year) {
        System.out.println("Report : INSURANCE STATEMENTS");
        System.out.println("From : 01/01/" + year + "To: 12/31/" + year);
        System.out.println("Sorted by: Established Date");
        System.out.println("Sort type : ASC\n");

        // Print the table header
        System.out.printf(TABLE_HEADER_2);

        System.out.println("----------------------------------------------------------------------------------------------");

        int i = 1;
        for (InsuranceInfo ins : list) {
            System.out.printf("%-4d %-12s %-15s %-15s %-15s %-18d $%,.0f%n",
                    i,
                    ins.getId(),
                    ins.getEstablishedDate(),
                    ins.getLicensePlate(),
                    ins.getOwner(),
                    ins.getPeriod(),
                    ins.getFees()
            );
            i++;
        }
    }

    public void showUninsured() {
        List<Car> list = findUninsured();
        if (list.isEmpty()) {
            System.out.println("List is empty!");
        } else {
            System.out.println("Report: UNINSURED CARS");
            System.out.println("Sorted by : Vehicle type");
            System.out.println("Sort type : DESC\n");

            System.out.print(TABLE_HEADER);

            System.out.println("----------------------------------------------------------------------------------------------");
            DecimalFormat df = new DecimalFormat("#,000");
            int i = 1;
            for (Car car : list) {
                System.out.printf("%-4d %-12s %-15s %-15s %-15s %-10d $%s\n",
                        i,
                        car.getLicensePlate(),
                        car.getRegDate(),
                        car.getOwner(),
                        car.getBrand(),
                        car.getNumberOfSeat(),
                        df.format(car.getValue())
                );
                i++;
            }
        }

    }

    public boolean isExist(String ID) {
        return this.containsKey(ID);
    }

    public void loadFromFile() {
        File f = new File(pathFile);
        if (f.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                while (fis.available() > 0) {
                    InsuranceInfo x = (InsuranceInfo) ois.readObject();
                    if (x != null) {
                        this.put(x.getId(), x);
                    }
                }
                ois.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Insurances.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Insurances.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insurances.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fis.close();
                } catch (IOException ex) {
                    Logger.getLogger(Insurances.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            System.out.println("insurances not found!");
        }
    }

    public void saveToFile() {
        File f = new File(pathFile);
        if (f.exists()) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for (InsuranceInfo i : this.values()) {
                    oos.writeObject(i);
                }
                oos.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Insurances.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Insurances.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Insurances.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("insurances not found!");
        }
    }

}
