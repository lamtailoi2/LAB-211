package business;

import java.util.HashMap;
import java.util.List;
import model.StatisticalInfo;
import model.Student;

public class Statistics extends HashMap<String, StatisticalInfo> {

    private final String HEADER_TABLE
            = "-----------------------------------------------------------\n"
            + "Peak Name           | Number of Participants | Total Cost \n"
            + "-----------------------------------------------------------";
    private final String FOOTER_TABLE
            = "-----------------------------------------------------------";

    public Statistics() {
        super();
    }

    public Statistics(List<Student> list) {
        super();
        statictiscalize(list);
    }

    public final void statictiscalize(List<Student> list) {
        for (Student i : list) {
            if (this.containsKey(i.getMountainCode())) {
                StatisticalInfo x = this.get(i.getMountainCode());
                x.setNumOfStudent(x.getNumOfStudent() + 1);
                x.setTotalCost(x.getTotalCost() + i.getTuitionFee());
            } else {
                this.put(i.getMountainCode(), new StatisticalInfo(i.getMountainCode(), 1, i.getTuitionFee()));
            }
        }
    }

    public void show() {
        System.out.println(HEADER_TABLE);
        for (StatisticalInfo i : this.values()) {
            System.out.println(i);
        }
        System.out.println(FOOTER_TABLE);
    }
}
