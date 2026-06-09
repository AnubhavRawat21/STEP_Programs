package CompanyOrder;

import java.util.ArrayList;

class Company {
    private String name;
    // private String Items;
    private ArrayList<Item> items;
    // private ArrayList<OrderItem> orderItems;
    private ArrayList<Customer> customers;

    public Company(String name) {
        this.name = name;
        this.items = new ArrayList<Item>();
        this.customers = new ArrayList<Customer>();
        // this.orderItems = new ArrayList<OrderItem>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public ArrayList<Customer> getCustomers() {
        return (ArrayList<Customer>) this.customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }
    
    public ArrayList<Item> getItems() {
        return (ArrayList<Item>) this.items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    // public ArrayList<OrderItem> getOrderItems(){
    //     return (ArrayList<OrderItem>) this.orderItems;
    // }

    // public void setOrderItems(ArrayList<OrderItem> orderItems){
    //     this.orderItems=orderItems;
    // }

    // public void addOrderItems(OrderItem orderItem){
    //     orderItems.add(orderItem);
    // }

    public double getTotalOrderValue()
    {
        double totalOrderValue=0.0;

        for(Customer customer:this.customers){
            for(Order order:customer.getOrder()){
                for(OrderItem orderItem:order.getOrderItems()){
                totalOrderValue+= orderItem.getQuantity()*orderItem.getItem().getRate();
                }

            }
        }
        return totalOrderValue;
    }

}