package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import model.Menu;

public class Menus {

    private String pathFile;

    public Menus() {
        this.pathFile = "./FeastMenu.csv";

    }

    private void readFromFile() {
        File f = new File(pathFile);
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        String temp = "";
        while ((temp = br.readLine()) != null) {

        }

    }

    private Menu textToObject(String temp) {
        Menu x = null;
        String[] split = temp.split(",");
        x = new Menu(split[0], split[1], split[2], split[3]);
        return x;
    }
}
