package business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Student;
import tool.Acceptable;

public class Students extends ArrayList<Student> {

    private final String HEADER_TABLE
            = "----------------------------------------------------------------------------\n"
            + "Student ID  | Name                | Phone       | Peak Code  | Fee        \n"
            + "----------------------------------------------------------------------------";
    private final String FOOTER_TABLE
            = "----------------------------------------------------------------------------\n";
    private static String pathFile = "./registration.dat";
    private boolean saved;

    public Students() {
        super();
        readFromFile();
        this.saved = true;
    }

    public boolean isSaved() {
        return saved;
    }

    public void showAll() {
        if (this.isEmpty()) {
            System.out.println("No students have registered yet\n");
            return;
        }
        System.out.println(HEADER_TABLE);
        for (Student i : this) {
            System.out.println(i);
        }
        System.out.println(FOOTER_TABLE);
    }

    public void showAll(List<Student> list) {
        if (list.isEmpty()) {
            System.out.println("No students have registered yet\n");
            return;
        }
        System.out.println(HEADER_TABLE);
        for (Student i : list) {
            System.out.println(i);
        }
        System.out.println(FOOTER_TABLE);
    }

    public void getStudentDetail(String ID) {
        Student x = searchByID(ID);
        if (x == null) {
            return;
        }
        DecimalFormat df = new DecimalFormat("#,##0");

        System.out.println("Student Details: ");
        System.out.printf(
                FOOTER_TABLE
                + "Student ID   : %s\n"
                + "Name         : %s\n"
                + "Phone        : %s\n"
                + "Mountain     : %s\n"
                + "Fee          : %s\n"
                + FOOTER_TABLE,
                x.getStudentID(), x.getName(), x.getPhone(), x.getMountainCode(), df.format(x.getTuitionFee())
        );
    }

    public boolean isExist(String ID) {
        Student x = searchByID(ID);
        return x != null;
    }

    public void addStudent(Student x) {
        if (x == null) {
            return;
        }
        if (this.add(x)) {
            this.saved = false;
        }
        System.out.println("Registered Successfully!\n");
    }

    public void updateStudent(Student newStudent) {
        Student existingStudent = searchByID(newStudent.getStudentID());
        if (existingStudent == null) {
            return;
        }
        existingStudent.setName(newStudent.getName().length() > 0 ? newStudent.getName() : existingStudent.getName());
        existingStudent.setPhone(newStudent.getPhone().length() > 0 ? newStudent.getPhone() : existingStudent.getPhone());
        existingStudent.setEmail(newStudent.getEmail().length() > 0 ? newStudent.getEmail() : existingStudent.getEmail());
        existingStudent.setMountainCode(newStudent.getMountainCode().length() > 0 ? newStudent.getMountainCode() : existingStudent.getMountainCode());
        existingStudent.setTuitionFee(
                (Acceptable.isValid(existingStudent.getPhone(), Acceptable.VIETTEL_VALID)
                || Acceptable.isValid(existingStudent.getPhone(), Acceptable.VNPT_VALID))
                ? 0.65 * 6000000
                : 6000000
        );
        this.saved = false;
        System.out.println("Updated successfully!\n");
    }

    public Student searchByID(String ID) {
        for (Student i : this) {
            if (i.getStudentID().equalsIgnoreCase(ID)) {
                return i;
            }
        }
        return null;
    }

    public void deleteStudent(String ID) {
        Student deletingStudent = searchByID(ID);
        if (deletingStudent == null) {
            System.out.println("This student has not registered yet\n");
            return;
        }
        if (this.remove(deletingStudent)) {
            this.saved = false;
            System.out.println("Deleted successfully!\n");
        }
    }

    public void searchByName(String name) {
        if (name == null) {
            return;
        }
        List<Student> tempList = new ArrayList();
        for (Student i : this) {
            if (i.getName().toLowerCase().endsWith(name.toLowerCase())) {
                tempList.add(i);
            }
        }
        if (!tempList.isEmpty()) {
            this.showAll(tempList);
        } else {
            System.out.println("No one matches the search criteria!");
        }
    }

    public List<Student> filterByCampusCode(String campusCode) {
        List<Student> list = new ArrayList();
        if (campusCode == null) {
            return null;
        }
        for (Student i : this) {
            if (i.getStudentID().startsWith(campusCode.toUpperCase())) {
                list.add(i);
            }
        }
        return list;
    }

    public void statisticalizeByMountainPeak() {
        Statistics x = new Statistics(this);
        x.show();
    }

    public void readFromFile() {
        File f = new File(pathFile);
        FileInputStream fis = null;
        if (f.exists()) {
            try {
                fis = new FileInputStream(f);
                if (fis.available() == 0) {
                    return;
                }
                ObjectInputStream ois = new ObjectInputStream(fis);
                while (fis.available() > 0) {
                    Student x = (Student) ois.readObject();
                    this.add(x);
                }
                ois.close();
                this.saved = true;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("File Not Found");
        }
    }

    public void saveToFile() {
        FileOutputStream fos = null;
        try {
            File f = new File(pathFile);
            fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Student i : this) {
                oos.writeObject(i);
            }
            oos.close();
            this.saved = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
