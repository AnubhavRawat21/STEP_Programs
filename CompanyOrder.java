import java.util.ArrayList;

class company {
    private String name;
    // private String Items;
    private ArrayList<Item> items;

    public company(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public ArrayList<Item> getItems() {
        return (ArrayList<Item>) this.items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void setName(String name) {
        this.name = name;
    }

}

class Customer {
    private String name;
    private String id;
    public Customer(String name,String id) {
        this.name = name;
        this.id=id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId(){
        return id;
    }
    public void setId() {
        this.id = id;
    }
}

class Item {
    private int itemNumber;
    private String title;
    private int rate;

    public Item(int itemNumber, String title, int rate) {
        this.itemNumber = itemNumber;
        this.title = title;
        this.rate = rate;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
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