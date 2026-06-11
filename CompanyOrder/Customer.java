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

    public double getTotalOrderValueV1()
    {
        double totalOrderValue=0.0;

        for(Order order:this.getOrder()){
            for(OrderItem orderItem:order.getOrderItems()){
                    totalOrderValue+= orderItem.getQuantity()*orderItem.getItem().getRate();
                }
            }
        return totalOrderValue;
    }

    public double getTotalOrderValueV2(){
        double totalOrderValue = 0.0;
       
        //No if else and any type of customer and still the logic will worl seamlessly without a change in this code
        //Satisfies OCP - Open Closed Principle
        for(Order order: this.getOrder()){
            totalOrderValue = order.getTotalOrderValue();
        }
        return totalOrderValue;
    }
 

}