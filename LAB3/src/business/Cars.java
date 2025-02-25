package business;

import java.util.HashMap;
import java.util.Iterator;
import model.Car;

public class Cars extends HashMap<String, Car> {

    public Cars() {
        super();
    }

    public void addCar(Car newCar) {
        this.put(newCar.getLicensePlate(), newCar);
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
        oldCar.setNumberOfSeat(newCar.getValue() > 999 ? newCar.getValue() : oldCar.getValue());
        oldCar.setBrand(newCar.getBrand().length() > 0 ? newCar.getBrand() : oldCar.getBrand());
        this.showAll();
        System.out.println("Update Successful!");
    }

    public void deleteCar(String licensePlate) {
        this.remove(licensePlate);
        System.out.println("The registration has been successfully deleted.");
    }
}
