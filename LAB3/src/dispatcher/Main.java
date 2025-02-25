package dispatcher;

import business.Cars;
import tool.Inputter;
import tool.DistrictMapper;
import tool.Acceptable;
import model.Car;

public class Main {

    private static Inputter input = new Inputter();
    private static Cars cars = new Cars();

    public static void main(String[] args) {
        String[] options = {
            "Add new car",
            "Find a car by license plate",
            "Update car information",
            "Delete car information",
            "Add an insurance statement",
            "List of insurance statements",
            "Report uninsured cars",
            "Save data",
            "Quit"
        };
        int choice;

        do {
            choice = Menu.getChoice(options);

            switch (choice) {
                case 1:
                    boolean isLoop = true;
                    do {
                        addCar();
                        String hd = input.inputAndLoop("Do you want to add another car [Y/N]: ", Acceptable.YN_VALID, "Invalid choice", false);
                        if (hd.compareToIgnoreCase("n") == 0) {
                            isLoop = false;
                        }
                    } while (isLoop);
                    break;
                case 2:
                    isLoop = true;
                    do {
                        findCarByLicensePlate();
                        String hd = input.inputAndLoop("Do you want to search another car [Y/N]: ", Acceptable.YN_VALID, "Invalid choice", false);
                        if (hd.compareToIgnoreCase("n") == 0) {
                            isLoop = false;
                        }
                    } while (isLoop);
                    break;
                case 3:
                    updateCar();
                    break;
                case 4:
                    deleteCar();
                    break;
                case 9:
                    System.out.println("Good bye!");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice
                != 9);
    }

    public static Car inputCar(String existLicense, boolean isUpdate) {
        Car newCar = new Car();
        if (!isUpdate) {
            boolean isLoop = true;
            while (isLoop) {
                String licensePlate = input.inputAndLoop("Enter License Plate: ", DistrictMapper::isValidCode, "Invalid License Plate", isUpdate);
                if (!cars.isExisted(licensePlate)) {
                    newCar.setLicensePlate(licensePlate);
                    isLoop = false;
                } else {
                    System.out.println("License Plate is existed");
                }
            }
            newCar.setValue(Integer.parseInt(input.inputAndLoop("Enter Car Value: ", Acceptable.VALUE_VALID, "Invalid Car Value", isUpdate)));
            newCar.setRegDate(input.inputAndLoop("Enter Registration Date: ", Acceptable::isValidFutureDate, "Invalid date", isUpdate));
        } else {
            newCar.setLicensePlate(existLicense);
        }
        newCar.setOwner(input.inputAndLoop("Enter Owner Name: ", Acceptable.NAME_VALID, "Invalid Name", isUpdate));
        newCar.setPhone(input.inputAndLoop("Enter Phone Number: ", Acceptable.PHONE_VALID, "Invalid Phone Number", isUpdate));
        newCar.setBrand(input.inputAndLoop("Enter Car Brand: ", Acceptable.BRAND_VALID, "Invalid Car Brand", isUpdate));
        String numberOfSeats = input.inputAndLoop("Enter Number of Seats: ", Acceptable.SEAT_VALID, "Invalid Number of Seat", isUpdate);
        newCar.setNumberOfSeat(numberOfSeats.length() > 0 ? Integer.parseInt(numberOfSeats) : 0);
        return newCar;

    }

    public static void addCar() {
        Car newCar = inputCar(null, false);
        cars.addCar(newCar);
    }

    public static void updateCar() {
        String licensePlate = input.inputAndLoop("Enter License Plate: ", DistrictMapper::isValidCode, "Invalid License Plate", true);
        if (cars.isExisted(licensePlate)) {
            cars.updateCar(inputCar(licensePlate, true));
        } else {
            System.out.println("License Plate do not exist!");
        }
    }

    public static void findCarByLicensePlate() {
        String licensePlate = input.inputAndLoop("Enter License Plate: ", DistrictMapper::isValidCode, "Invalid License Plate", true);
        Car x = cars.findCarByLicensePlate(licensePlate);
        if (x != null) {
            System.out.println(x);
        } else {
            System.out.println("No one matches the search criteria!");
        }
    }

    public static void deleteCar() {
        String licensePlate = input.inputAndLoop("Enter License Plate: ", DistrictMapper::isValidCode, "Invalid License Plate", true);
        Car x = cars.findCarByLicensePlate(licensePlate);
        if (x != null) {
            System.out.println(x);
            String hd = input.inputAndLoop("Are you sure you want to delete this registration? (Y/N): ", Acceptable.YN_VALID, "Invalid choice", false);
            if (hd.compareToIgnoreCase("y") == 0) {
                cars.deleteCar(licensePlate);
                System.out.println("The registration has been successfully deleted.");
            }
        } else {
            System.out.println("No one matches the search criteria!");
        }

    }
}
