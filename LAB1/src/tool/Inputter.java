package tool;

import java.util.Scanner;
import java.util.function.Predicate;
import model.Student;
import business.Students;
import static dispatcher.Main.input;
import static dispatcher.Main.mountainList;
import static dispatcher.Main.studentList;

public class Inputter {

    private static Scanner sc;

    public Inputter() {
        this.sc = new Scanner(System.in);
    }

    public String getString(String msg) {
        System.out.print(msg);
        return this.sc.nextLine();
    }

    public int getInt(String msg) {
        int res = 0;
        String temp = getString(msg);
        if (Acceptable.isValid(temp, Acceptable.INT_VALID)) {
            res = Integer.parseInt(temp);
        }
        return res;
    }

    public double getDouble(String msg) {
        double res = 0;
        String temp = getString(msg);
        if (Acceptable.isValid(temp, Acceptable.DOUBLE_VALID)) {
            res = Double.parseDouble(temp);
        }
        return res;
    }

    /**
     * @param msg
     * @param pattern
     * @return
     */
    public String inputAndLoop(String msg, String pattern, String errorMsg, boolean isUpdate) {
        String res = "";
        boolean isLoop = true;
        do {
            res = getString(msg);
            isLoop = !Acceptable.isValid(res, pattern);
            if (isLoop && !isUpdate) {
                System.out.println(errorMsg);
            }
        } while (isLoop && !isUpdate);
        return res;
    }

    public String inputAndLoop(String msg, Predicate<String> validator, String errorMessage, boolean isUpdate) {
        String res = "";
        do {
            res = getString(msg);
            if (!validator.test(res) && !isUpdate) {
                System.out.println(errorMessage);
            }
        } while (!validator.test(res) && !isUpdate);
        return res;
    }

    /**
     * @param msg
     * @param pattern
     * @param validator
     * @param errorMsg
     * @param validationErrorMsg
     * @param invertValidator If true, the validator result will be negated
     * @return
     */
    public String inputAndLoop(String msg, String pattern, Predicate<String> validator, String errorMsg, String validationErrorMsg, boolean invertValidator, boolean isUpdate) {
        String res = "";
        boolean isLoop = true;
        do {
            res = getString(msg);
            if (!Acceptable.isValid(res, Acceptable.STU_ID_VALID) && !isUpdate) {
                System.out.println(errorMsg);
            } else if (invertValidator ? validator.test(res) : !validator.test(res) && !isUpdate) {
                System.out.println(validationErrorMsg);
            } else {
                isLoop = false;
            }
        } while (isLoop && !isUpdate);
        return res;
    }

}
