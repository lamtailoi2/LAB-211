package business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Car;

public class Cars extends HashMap<String, Car> {

    private final String pathFile = "./cars.dat";
    private boolean saved;

    public Cars() {
        super();
        loadFromFile();
        this.saved = true;
    }

    public boolean isSaved() {
        return this.saved;
    }

    public void addCar(Car newCar) {
        this.put(newCar.getLicensePlate(), newCar);
        this.saved = false;
        System.out.println("Add Successful");
    }

    public void showAll() {
        Iterator itr = this.values().iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    public Car findCarByLicensePlate(String licensePlate) {
        return this.get(licensePlate.toUpperCase());
    }

    public boolean isExisted(String licensePlate) {
        return this.containsKey(licensePlate.toUpperCase());
    }

    public void updateCar(Car newCar) {
        Car oldCar = this.get(newCar.getLicensePlate());
        oldCar.setOwner(newCar.getOwner().length() > 0 ? newCar.getOwner() : oldCar.getOwner());
        oldCar.setPhone(newCar.getPhone().length() > 0 ? newCar.getPhone() : oldCar.getPhone());
        oldCar.setNumberOfSeat(newCar.getValue() > 0 ? newCar.getValue() : oldCar.getValue());
        oldCar.setBrand(newCar.getBrand().length() > 0 ? newCar.getBrand() : oldCar.getBrand());
        this.showAll();
        this.saved = false;
        System.out.println("Update Successful!");
    }

    public void deleteCar(String licensePlate) {
        this.remove(licensePlate.toUpperCase());
        System.out.println(">>>>>");
        showAll();
        this.saved = false;
        System.out.println("The registration has been successfully deleted.");
    }

    public void loadFromFile() {
        File f = new File(pathFile);
        if (f.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                while (fis.available() > 0) {
                    Car x = (Car) ois.readObject();
                    if (x != null) {
                        this.put(x.getLicensePlate(), x);
                    }
                }
                ois.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Cars.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Cars.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Cars.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fis.close();
                } catch (IOException ex) {
                    Logger.getLogger(Cars.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void saveToFile() {
        File f = new File(pathFile);
        if (f.exists()) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for (Car i : this.values()) {
                    oos.writeObject(i);
                }
                this.saved = true;
                oos.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Cars.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Cars.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fos.close();
                    System.out.println("cars.dat File Save Successful!");
                } catch (IOException ex) {
                    Logger.getLogger(Cars.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            System.out.println("cars.dat not found!");
        }
    }

}
