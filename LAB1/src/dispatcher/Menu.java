package dispatcher;

import java.util.Scanner;
import tool.Inputter;
public class Menu {
    private static Inputter input = new Inputter();    
    public static int getChoice(Object[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("Your options from 1 - " + options.length + ": ");
        return input.getInt("");
    }
}
