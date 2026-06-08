package ComapnyOrder;

import java.util.ArrayList;

public class TestMain {
    public static void TestMain(String[] args) {
        Company c1 = new Company("Tech Innovators");
        Company c2 = new Company("Health Solutions");
        Company c3 = new Company("Green Energy");

        ArrayList<Company> companies = new ArrayList<>();
        companies.add(c1);
        companies.add(c2);
        companies.add(c3);

        for (Company c : companies) {
            System.out.println(c.getName());
        }
    }
}