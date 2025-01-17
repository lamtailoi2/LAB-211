package tool;

import java.util.Scanner;

public class Inputter {

    private static Scanner sc;

    public Inputter() {
        this.sc = new Scanner(System.in);
    }

    /**
     *
     * @param msg
     * @return
     */
    public String getString(String msg) {
        System.out.print(msg);
        return this.sc.nextLine();
    }

//    public int getInt(String msg) {
//        int res = 0;
//        String temp = getString(msg);
//        if (Acceptable.isValid(temp, Acceptable.INT_VALID)) {
//            res = Integer.parseInt(temp);
//        }
//        return res;
//    }
//
//    public double getDouble(String msg) {
//        double res = 0;
//        String temp = getString(msg);
//        if (Acceptable.isValid(temp, Acceptable.DOUBLE_VALID)) {
//            res = Double.parseDouble(temp);
//        }
//        return res;
//    }
    public String inputAndLoop(String msg, String pattern, String errorMsg, boolean isUpdate) {
        String res = "";
        boolean isLoop = true;
        do {
            res = getString(msg);
            isLoop = !Acceptable.isValid(res, pattern);
            if (isLoop) {
                System.out.println(errorMsg);
            }
        } while (isLoop);
        return res;
    }
}
