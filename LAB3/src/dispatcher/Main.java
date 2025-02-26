package dispatcher;

import business.Cars;
import business.Insurances;
import java.util.ArrayList;
import java.util.List;
import tool.Inputter;
import tool.DistrictMapper;
import tool.Acceptable;
import model.Car;
import model.InsuranceInfo;

public class Main {

    private static Inputter input = new Inputter();
    private static Cars cars = new Cars();
    private static Insurances insurances = new Insurances(cars);

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
                    isLoop = true;
                    do {
                        updateCar();
                        String hd = input.inputAndLoop("Do you want to update another car [Y/N]: ", Acceptable.YN_VALID, "Invalid choice", false);
                        if (hd.compareToIgnoreCase("n") == 0) {
                            isLoop = false;
                        }
                    } while (isLoop);
                    break;
                case 4:
                    deleteCar();
                    break;
                case 5:
                    isLoop = true;
                    do {
                        addInsurance();
                        String hd = input.inputAndLoop("Do you want to update another insurance [Y/N]: ", Acceptable.YN_VALID, "Invalid choice", false);
                        if (hd.compareToIgnoreCase("n") == 0) {
                            isLoop = false;
                        }
                    } while (isLoop);
                    break;
                case 6:
                    isLoop = true;
                    do {
                        findByYear();
                        String hd = input.inputAndLoop("Do you want to find another insurance [Y/N]: ", Acceptable.YN_VALID, "Invalid choice", false);
                        if (hd.compareToIgnoreCase("n") == 0) {
                            isLoop = false;
                        }
                    } while (isLoop);
                    break;
                case 7:
                    report();
                    break;
                case 8:
                    save();
                    break;
                case 9:
                    if (cars.isSaved() && insurances.isSaved()) {
                        System.out.println("Bye");
                    } else {
                        String hd = input.inputAndLoop("There're some changed. Do you to want to save it [Y/N]: ", Acceptable.YN_VALID, "Invalid choice", false);
                        if (hd.compareToIgnoreCase("y") == 0) {
                            save();
                            System.out.println("Bye");
                        } else {
                            System.out.println("Bye");
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 9);
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

    public static InsuranceInfo inputInsurance() {
        InsuranceInfo ins = new InsuranceInfo();
        boolean isLoop = true;
        while (isLoop) {
            String ID = input.inputAndLoop("Enter Insurance ID: ", Acceptable.ID_VALID, "Invalid Insurance ID", false);
            if (!insurances.isExist(ID)) {
                ins.setId(ID);
                isLoop = false;
            } else {
                System.out.println("ID is existed!");
            }
        }
        isLoop = true;
        while (isLoop) {
            String licensePlate = input.inputAndLoop("Enter License Plate: ", DistrictMapper::isValidCode, "Invalid License Plate", false);
            if (cars.isExisted(licensePlate)) {
                ins.setLicensePlate(licensePlate);
                ins.setOwner(cars.findCarByLicensePlate(licensePlate).getOwner());
                isLoop = false;
            } else {
                System.out.println("License Plate is not existed");
            }
        }
        ins.setEstablishedDate(input.inputAndLoop("Enter Established Date: ", Acceptable::isValidFutureDate, "Invalid Date", false));
        ins.setPeriod(Integer.parseInt(input.inputAndLoop("Enter Period: ", Acceptable.PERIOD_VALID, "Invalid Period", false)));
        return ins;

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
            System.out.println("No one matches the search criteria!");
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
            }
        } else {
            System.out.println("No one matches the search criteria!");
        }

    }

    public static void addInsurance() {
        InsuranceInfo ins = inputInsurance();
        insurances.addInsurance(ins);

    }

    public static void findByYear() {
        int year = input.getInt("Enter year: ");
        List<InsuranceInfo> list = insurances.findByYear(year);
        if (list.isEmpty()) {
            System.out.println("List is empty!");
        } else {
            insurances.showAll(list, year);
        }
    }

    public static void report() {
        insurances.showUninsured();
    }

    public static void save() {
        cars.saveToFile();
        insurances.saveToFile();
    }
}
