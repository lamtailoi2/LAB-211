package dispatcher;

import business.Customers;
import business.FeastMenus;
import model.Customer;
import model.FeastMenu;
import tool.Acceptable;
import tool.Inputter;

public class Main {

    private static Inputter input = new Inputter();
    private static FeastMenus feastMenu = new FeastMenus();
    private static Customers customerList = new Customers();

    public static void main(String[] args) {
        String[] options = {
            "Register customers.",
            "Update customer information.",
            "Search for customer information by name.",
            "Display feast menus.",
            "Place a feast order.",
            "Update order information.",
            "Save data to file.",
            "Display Customer or Order lists",
            "Exit"
        };
        int choice;
        do {
            choice = Menu.getChoice(options);

            switch (choice) {
                case 1:
                    boolean isLoop = true;
                    do {
                        addCustomer();
                        String hd = input.inputAndLoop("Do you want to continue entering new customer [Y/N]: ", Acceptable.YN_VALID, "Invalid choice", false);
                        if (hd.compareToIgnoreCase("n") == 0) {
                            isLoop = false;
                        }
                    } while (isLoop);
                    break;
                case 2:
                    isLoop = true;
                    do {
                        updateCustomer();
                        String hd = input.inputAndLoop("Do you want to update another customer [Y/N]: ", Acceptable.YN_VALID, "Invalid choice", false);
                        if (hd.compareToIgnoreCase("n") == 0) {
                            isLoop = false;
                        }
                    } while (isLoop);
                    break;
                case 3:
                    searchByName();
                    break;
                case 7:
                    customerList.saveToFile();
                    customerList.showAll();
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 9);
    }

    public static Customer inputCustomer(boolean isUpdate, String existingCode) {
        Customer x = new Customer();
        if (isUpdate) {
            x.setCode(existingCode);
        } else {
            while (true) {
                String code = input.inputAndLoop("Enter Customer's Code: ", Acceptable.CUS_CODE_VALID, "Customer's Code is invalid. Re-enter ....", isUpdate);
                if (!customerList.isExisted(code)) {
                    x.setCode(code);
                    break;
                } else {
                    System.out.println("This customer is already existed! Try again");
                }
            }
        }
        x.setName(input.inputAndLoop("Enter Customer's Name: ", Acceptable.NAME_VALID, "Customer's Name is invalid. Re-enter ....", isUpdate));
        x.setEmail(input.inputAndLoop("Enter Customer's Name: ", Acceptable.EMAIL_VALID, "Customer's Name is invalid. Re-enter ....", isUpdate));
        x.setPhone(input.inputAndLoop("Enter Customer's Phone: ", Acceptable.PHONE_VALID, "Customer's Phone is invalid. Re-enter ....", isUpdate));
        return x;
    }

    public static void addCustomer() {
        Customer x = inputCustomer(false, null);
        customerList.addCustomer(x);
    }

    public static void updateCustomer() {
        String code = input.inputAndLoop("Enter Customer's Code: ", Acceptable.CUS_CODE_VALID, "Customer's Code is invalid. Re-enter ....", false);
        if (customerList.isExisted(code)) {
            customerList.updateCustomer(inputCustomer(true, code));
        } else {
            System.out.println("This customer does not exist");
        }
    }

    public static void searchByName() {
        String name = input.getString("Enter Customer's Name [2-20 characters]: ");
        customerList.searchByName(name);
    }
}
