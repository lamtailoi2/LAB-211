package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import business.Statistics;
import model.Mountain;

public class Mountains extends ArrayList<Mountain> {

    private String pathFile = "./MountainList.csv";

    public Mountains() {
        super();
        readFormFile();
    }

    public void show() {
        for (Mountain i : this) {
            System.out.println(i);
        }
    }

    public void readFormFile() {
        FileReader fr = null;
        try {
            File f = new File(pathFile);
            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            String temp = "";
            while ((temp = br.readLine()) != null) {
                Mountain x = textToObject(temp);
                if (x != null) {
                    this.add(x);
                }
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Mountains.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Mountains.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Mountains.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private Mountain textToObject(String temp) {
        Mountain x = null;
        try {
            String[] split = temp.split(",");
            x = new Mountain(split[0], split[1], split[2], split[3]);
        } catch (Exception e) {

        }
        return x;
    }

    public boolean isValidMountainCode(String code) {
        for (Mountain i : this) {
            if (i.getMountainCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

}
