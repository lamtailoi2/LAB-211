package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.FeastMenu;

public class FeastMenus extends ArrayList<FeastMenu> {

    private final String pathFile = "./FeastMenu.csv";

    public FeastMenus() {
        super();
        readFromFile();
    }

    public void showAll() {
        if (!this.isEmpty()) {
            for (FeastMenu i : this) {
                System.out.println(i);
                System.out.println("----------------------------------------");
            }
        } else {
            System.out.println("The Menu is Empty");
        }

    }

    private void readFromFile() {
        FileReader fr = null;
        File f = new File(pathFile);
        if (f.exists()) {
            try {

                fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                br.readLine();
                String temp = "";
                while ((temp = br.readLine()) != null) {
                    FeastMenu x = textToObject(temp);
                    if (x != null) {
                        this.add(x);
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FeastMenus.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FeastMenus.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fr.close();
                } catch (IOException ex) {
                    Logger.getLogger(FeastMenus.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("Cannot read data from “feastMenu.csv”. Please check it.");
        }
    }

    private FeastMenu textToObject(String temp) {
        FeastMenu x = null;
        String[] split = temp.split(",");
        x = new FeastMenu(split[0], split[1], Integer.parseInt(split[2]), split[3]);
        return x;
    }

    public FeastMenu findFeastMenuByCode(String code) {
        for (FeastMenu i : this) {
            if (i.getCode().compareToIgnoreCase(code) == 0) {
                return i;
            }
        }
        return null;
    }

    public boolean isValidCode(String code) {
        return findFeastMenuByCode(code) != null;
    }

}
