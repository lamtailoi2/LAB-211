package dispatcher;

import business.Customers;
import business.FeastMenus;
import business.Orders;
import model.Customer;
import model.Order;
import tool.Acceptable;
import tool.Inputter;

public class Main {

    private static Inputter input = new Inputter();
    private static FeastMenus feastMenu = new FeastMenus();
    private static Customers customerList = new Customers();
    private static Orders orders = new Orders(customerList);

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
                case 4:
                    showMenu();
                    break;
                case 5:
                    placeOrder();
                    break;
                case 6:
                    updateOrder();
                    break;
                case 7:
                    save();
                    break;
                case 8:
                    String[] showOptions = {
                        "Show Customer List",
                        "Show Order List"
                    };
                    int selection = Menu.getChoice(showOptions);
                    switch (selection) {
                        case 1:
                            customerList.showAll();
                            break;
                        case 2:
                            orders.showAll();
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                    break;
                case 9:
                    if (orders.isSaved() && customerList.isSaved()) {
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

    public static Customer inputCustomer(boolean isUpdate, String existingCode) {
        Customer x = new Customer();
        if (isUpdate) {
            x.setCode(existingCode);
        } else {
            while (true) {
                String code = input.inputAndLoop("Enter Customer's Code [C|K|Gxxxx]: ", Acceptable.CUS_CODE_VALID, "Customer's Code is invalid. Re-enter ....", isUpdate);
                if (!customerList.isExisted(code)) {
                    x.setCode(code);
                    break;
                } else {
                    System.out.println("This customer is already existed! Try again");
                }
            }
        }
        x.setName(input.inputAndLoop("Enter Customer's Name [2-25 characters]: ", Acceptable.NAME_VALID, "Customer's Name is invalid. Re-enter ....", isUpdate));
        x.setEmail(input.inputAndLoop("Enter Customer's Email: ", Acceptable.EMAIL_VALID, "Customer's Name is invalid. Re-enter ....", isUpdate));
        x.setPhone(input.inputAndLoop("Enter Customer's Phone: ", Acceptable.PHONE_VALID, "Customer's Phone is invalid. Re-enter ....", isUpdate));
        return x;
    }

    public static Order inputOrder(boolean isUpdate, String oldID) {
        Order o = null;
        if (!isUpdate) {
            o = new Order();
            while (true) {
                String code = input.inputAndLoop("Enter Customer's Code [C|K|Gxxxx]: ", Acceptable.CUS_CODE_VALID, "Customer's Code is invalid. Re-enter ....", isUpdate);
                if (customerList.isExisted(code)) {
                    o.setCustomerCode(code);
                    break;
                } else {
                    System.out.println("This customer is not existed! Try again");
                }
            }
        } else {
            o = new Order(oldID);
            o.setCustomerCode(input.inputAndLoop("Enter Customer's Code [C|K|Gxxxx]: ", Acceptable.CUS_CODE_VALID, "Customer's Code is invalid. Re-enter ....", isUpdate));
        }

        o.setMenuCode(input.inputAndLoop("Enter Menu's Code [PW00x]: ", feastMenu::isValidCode, "Invalid Menu Code. Enter again ...", isUpdate));
        String numTableInput = input.inputAndLoop("Enter number of table [Number > 0]: ",
                Acceptable.POS_INT_VALID,
                "Invalid number. Enter again",
                isUpdate);
        o.setNumOfTable(numTableInput.trim().isEmpty() ? 0 : Integer.parseInt(numTableInput));
        o.setEventDate(input.inputAndLoop("Enter preferred event date [dd/mm/yyyy]: ", Acceptable::isValidFutureDate, "Invalid date. Enter again ...", isUpdate));
        return o;
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

    public static void showMenu() {
        feastMenu.showAll();
    }

    public static void placeOrder() {
        Order o = inputOrder(false, null);
        orders.placeOrder(o);
    }

    public static void updateOrder() {
        String ID = input.getString("Enter Order ID: ");
        if (orders.isValidID(ID)) {
            Order newOrder = inputOrder(true, ID);
            orders.updateOrder(newOrder);
        } else {
            System.out.println("Order does not exist!");
        }
    }

    public static void save() {
        customerList.saveToFile();
        orders.saveToFile();
    }
}
