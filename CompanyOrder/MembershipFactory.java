package CompanyOrder;

public class MembershipFactory{
    public Membership getMembership(String membershipType){
        var plat= new Membership();
        if (membershipType.equalsIgnoreCase("platinum")){
            
            plat.SetMembershipType("Platinum");

            plat.setDiscount(20.0);
            plat.setfees(20000.0);
        }
        if (membershipType.equalsIgnoreCase("gold")){
            
            plat.SetMembershipType("gold");
            plat.setDiscount(10.0);
            plat.setfees(10000.0);
        }
        if (membershipType.equalsIgnoreCase("silver")){
            plat.SetMembershipType("silver");
            plat.setDiscount(5.0);
            plat.setfees(5000.0);
        }
        return null;
    }
    

}