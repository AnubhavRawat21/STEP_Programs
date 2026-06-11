package CompanyOrder;

public class Membership {
    private double discount;
    private double fees;
    private String membershipType;

    public double getDiscount(){
        return this.discount;
    }
    
    public void setDiscount(double discount){
        this.discount=discount;
    }

    public double getfees(){
        return this.fees;
    }
    
    public void setfees(double fees){
        this.fees=fees;
    }

    public void SetMembershipType(String membershipType){
        this.membershipType=membershipType;
    }
    
    public String GetMembershipType(){
        return this.membershipType;
    }
}
