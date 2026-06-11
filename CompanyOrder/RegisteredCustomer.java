package CompanyOrder;

class RegisteredCustomer extends Customer {
    // private double discount;
    // private double fees;
    // private String membershipType;

    // public double getDiscount(){
    //     return this.discount;
    // }
    
    // public void setDiscount(double discount){
    //     this.discount=discount;
    // }

    // public double getfees(){
    //     return this.discount;
    // }
    
    // public void setfees(double fees){
    //     this.fees=fees;
    // }

    public RegisteredCustomer(String name, String customerId) {
        super(name, customerId);
    }

    // public void SetMembershipType(String membershipType){
    //     this.membershipType=membershipType;
    //     if (membershipType.equalsIgnoreCase("platinum")){
    //         this.discount=20.0;
    //         this.fees=20000.0;
    //     }
    //     if (membershipType.equalsIgnoreCase("gold")){
    //         this.discount=10.0;
    //         this.fees=10000.0;
    //     }
    //     if (membershipType.equalsIgnoreCase("silver")){
    //         this.discount=5.0;
    //         this.fees=5000.0;
    //     }
    // }
    
    // public String GetMembershipType(){
    //     return this.membershipType;
    // }

    private Membership membership;

    public Membership getMembers(){
        return this.membership;
    }

    public void SetMembership(Membership membership){
        this.membership= membership;
    }

    @Override
    public double getTotalOrderValueV2()
    {
        double totalOrderValue=0.0;

        for(Order order:this.getOrder()){
            for(OrderItem orderItem:order.getOrderItems()){
                    totalOrderValue= order.getTotalOrderValue()*(1-this.membership.getDiscount());
                }
            }
        return totalOrderValue;
    }
    
}
