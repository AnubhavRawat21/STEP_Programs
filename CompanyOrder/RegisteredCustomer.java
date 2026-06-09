package CompanyOrder;

class RegisteredCustomer extends Customer {
    private double discount;

    public double getDiscount(){
        return this.discount;
    }
    
    public void setDiscount(double discount){
        this.discount=discount;
    }

    public RegisteredCustomer(String name, String customerId, double discount) {
        super(name, customerId);
        this.discount=discount;
    }
    
        
    
}
