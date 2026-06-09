package CompanyOrder;

import java.util.ArrayList;

class Customer {
    private String name;
    private String customerId;
    private ArrayList<Order> orders;
 
    public Customer(String name,String customerId) {
        this.name = name;
        this.customerId= customerId;
        this.orders= new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId(){
        return this.customerId;
    }

    public void setId(String customerId) {
        this.customerId = customerId;
    }

    public ArrayList<Order> getOrder(){
        return (ArrayList<Order>) this.orders;
    }

    public void addOrder(Order order){
        orders.add(order);
    }
}