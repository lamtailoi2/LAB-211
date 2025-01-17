package dispatcher;

import tool.Acceptable;
import tool.Inputter;
import business.Mountains;
import business.Students;
import java.util.List;
import model.Student;

/*
    Optimize Input
    Fix ReadFile Error
 */
public class Main {

    public static Students studentList = new Students();
    public static Mountains mountainList = new Mountains();
    public static Inputter input = new Inputter();

    public static void main(String[] args) {
        String[] options
                = {
                    "Add a new student registration",
                    "Update registration information",
                    "Display Registered List",
                    "Delete Registration Information",
                    "Search Participants by Name",
                    "Filter Data by Campus",
                    "Statistics of Registration Numbers by Location",
                    "Save Data to File",
                    "Exit the Program"
                };
        int choice = 0;
        do {
            choice = Menu.getChoice(options);
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    updateStudent();
                    break;
                case 3:
                    show();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    searchByName();
                    break;
                case 6:
                    filterByCampus();
                    break;
                case 7:
                    getStatistics();
                    break;
                case 8:
                    save();
                    break;
                case 9:
                    if (!studentList.isSaved()) {
                        String hd = input.inputAndLoop("There have been some changes. Do you want to save them? (Y/N): ", Acceptable.DELETE_VALID, "Invalid choice! Re-enter ...", true);
                        if (hd.compareToIgnoreCase("y") == 0) {
                            studentList.saveToFile();
                        }
                    }
                    System.out.println("See you next time!");
                    break;
                default:
                    System.out.println("This function is not available");
            }
        } while (choice != 9);
    }

    public static Student inputStudent(boolean isUpdate, Student existingStudent) {
        Student x = new Student();
        if (isUpdate) {
            x.setStudentID(existingStudent.getStudentID());
        } else {
            x.setStudentID(input.inputAndLoop("Enter Student's ID [SE|HE|QE|CExxxxxx]: ", Acceptable.STU_ID_VALID, studentList::isExist, "Student's ID is Invalid. Re-enter ...", "Student's ID is Existed. Re-enter ...", true, isUpdate));
        }
        x.setName(input.inputAndLoop("Enter Student's Name [2-20 characters]: ", Acceptable.NAME_VALID, "Student's Name is Invalid. Re-enter ...", isUpdate));
        x.setPhone(input.inputAndLoop("Enter Student's Phone:  ", Acceptable.PHONE_VALID, "Student's Number is Invalid. Re-enter ...", isUpdate));
        x.setEmail(input.inputAndLoop("Enter Student's Email: ", Acceptable.EMAIL_VALID, "Email is Invalid. Re-enter ...", isUpdate));
        x.setMountainCode(input.inputAndLoop("Enter Mountan's Code: ", mountainList::isValidMountainCode, "Mountain Code is Invalid. Re-enter ...", isUpdate));
        x.setTuitionFee(Acceptable.isValid(x.getPhone(), Acceptable.VIETTEL_VALID)
                || Acceptable.isValid(x.getPhone(), Acceptable.VIETTEL_VALID) ? 6000000 * 0.65 : 6000000);
        return x;
    }

    public static void addStudent() {
        Student x = inputStudent(false, null);
        studentList.addStudent(x);
    }

    public static void updateStudent() {
        String ID = input.inputAndLoop("Enter Student's ID: ", Acceptable.STU_ID_VALID, "Student's ID is Invalid. Re-enter ...", false);
        Student existingStudent = studentList.searchByID(ID);
        if (existingStudent != null) {
            studentList.updateStudent(inputStudent(true, existingStudent));
        } else {
            System.out.println("This student has not registered yet\n");
        }
    }

    public static void show() {
        studentList.showAll();
    }

    public static void deleteStudent() {
        String ID = input.inputAndLoop("Enter Student's ID [SE|HE|QE|CExxxxxx]: ", Acceptable.STU_ID_VALID, "Student's ID is Invalid. Re-enter ...", false);
        if (studentList.searchByID(ID) != null) {
            studentList.getStudentDetail(ID);
            String choice = input.inputAndLoop("Are you sure you want to delete this registration? (Y/N): ", Acceptable.DELETE_VALID, "Invalid choice! Re-enter ...", false);
            if (choice.compareToIgnoreCase("y") == 0) {
                studentList.deleteStudent(ID);
                System.out.println("The registration has been successfully deleted\n");
            }
        } else {
            System.out.println("This student has not registered yet\n");
        }

    }

    public static void searchByName() {
        String name = input.getString("Enter Student's Name [2-20 characters]: ");
        studentList.searchByName(name);
    }

    public static void filterByCampus() {
        String campus = input.inputAndLoop("Enter Campus's Code [SE|HE|QE|CE]: ", Acceptable.CAMPUS_VALID, "Campus is invalid. Re-enter ...", true);
        List<Student> filteredList = studentList.filterByCampusCode(campus);
        studentList.showAll(filteredList);
    }

    public static void getStatistics() {
        studentList.statisticalizeByMountainPeak();
    }

    public static void save() {
        studentList.saveToFile();
        System.out.println("Saved Successfully!\n");
    }
}
