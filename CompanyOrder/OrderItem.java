package CompanyOrder;

public class OrderItem {
    private Item item;
    private int quantity;

    public OrderItem(Item item, int quantity)
    {
        this.item=item;
        this.quantity=quantity;
    }

    public void setItem(Item item ){
        this.item=item;
    }

    public Item getItem(){
        return this.item;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void setQuantity(int quantity){
        this.quantity= quantity;
    }
}
