package CompanyOrder;

import java.util.ArrayList;
import java.util.List;

class Order {
    private int orderId;
    private Customer customer;
    private ArrayList<OrderItem> orderItems;

    public Order(){
        orderItems=new ArrayList<>();
    }

    public int getOrderId(){
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId=orderId;
    }
    
    public Customer getCustomer(){
        return this.customer;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public List<OrderItem> getOrderItems()
    {
        return this.orderItems;
    }

    public void addOrderItem(OrderItem orderItem)
    {
        this.orderItems.add(orderItem);
    }
    
    public double getTotalOrderValue()
    {
        double totalOrderValue=0.0;
        for (OrderItem o: this.getOrderItems()){
            totalOrderValue = o.getOrderItemsValue();}
        return totalOrderValue;
    }
}
