package ComapnyOrder;

import java.util.ArrayList;

class Company {
    private String name;
    // private String Items;
    private ArrayList<Item> items;

    public Company(String name) {
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