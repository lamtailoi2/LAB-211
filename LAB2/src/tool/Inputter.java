package tool;

import java.util.Scanner;
import java.util.function.Predicate;

public class Inputter {

    private static Scanner sc;

    public Inputter() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Reads a line of text input from the user after displaying the specified
     * message.
     *
     * @param msg The message to display to the user.
     * @return The input string entered by the user.
     */
    public String getString(String msg) {
        System.out.print(msg);
        return this.sc.nextLine();
    }

    /**
     * Reads an integer input from the user after displaying the specified
     * message. Validates the input using a pre-defined integer pattern.
     *
     * @param msg The message to display to the user.
     * @return The integer value parsed from the user input, or 0 if the input
     * is invalid.
     */
    public int getInt(String msg) {
        int res = 0;
        String temp = getString(msg);
        if (Acceptable.isValid(temp, Acceptable.INT_VALID)) {
            res = Integer.parseInt(temp);
        }
        return res;
    }

    /**
     * Repeatedly prompts the user for input until a valid string is entered.
     * The input is validated using a regular expression pattern.
     *
     * @param msg The message to display to the user.
     * @param pattern The regular expression pattern to validate the input.
     * @param errorMsg The error message to display if validation fails.
     * @param isUpdate If true, skips validation checks during an update
     * process.
     * @return A valid string entered by the user.
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

    /**
     * Repeatedly prompts the user for input until a valid string is entered.
     * The input is validated using a custom predicate validator.
     *
     * @param msg The message to display to the user.
     * @param validator A predicate that tests the validity of the input.
     * @param errorMsg The error message to display if validation fails.
     * @param isUpdate If true, skips validation checks during an update
     * process.
     * @return A valid string entered by the user.
     */
    public String inputAndLoop(String msg, Predicate<String> validator, String errorMsg, boolean isUpdate) {
        String res = "";
        boolean isLoop = true;
        do {
            res = getString(msg);
            isLoop = !validator.test(res);
            if (isLoop && !isUpdate) {
                System.out.println(errorMsg);
            }
        } while (isLoop && !isUpdate);
        return res;
    }
    
}
