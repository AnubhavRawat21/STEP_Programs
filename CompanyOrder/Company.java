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

    public double getTotalOrderValueV1()
    {
        double totalOrderValue=0.0;

        for(Customer customer:this.customers){
            for(Order order:customer.getOrder()){
                for(OrderItem orderItem:order.getOrderItems()){
                    // if (customer instanceof RegisteredCustomer){
                    //     RegisteredCustomer rc= (RegisteredCustomer) customer;
                    //     double discount= rc.getDiscount();
                    //     totalOrderValue+= orderItem.getQuantity()*orderItem.getItem().getRate() * (1-discount)/100;
                    // }
                    // else{
                    //     totalOrderValue+= orderItem.getQuantity()*orderItem.getItem().getRate();
                    // }
                }

            }
        }
        return totalOrderValue;
    }

    public double getTotalOrderValueV2(){
        double totalOrderValue = 0.0;
       
        //No if else and any type of customer and still the logic will worl seamlessly without a change in this code
        //Satisfies OCP - Open Closed Principle
        for(Customer customer: this.customers){
            totalOrderValue = customer.getTotalOrderValueV2();
        }
        
        return totalOrderValue;
    }

}