import java.util.ArrayList;

class company {
    private String name;

    public company(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


public class CompanyOrder {
    public static void main(String[] args) {
        company c1 = new company("Tech Innovators");
        company c2 = new company("Health Solutions");
        company c3 = new company("Green Energy");

        ArrayList<company> companies = new ArrayList<>();
        companies.add(c1);
        companies.add(c2);
        companies.add(c3);

        for (company c : companies) {
            System.out.println(c.getName());
        }
    }
}