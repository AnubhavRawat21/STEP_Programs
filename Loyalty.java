import java.util.Scanner;

public class Loyalty {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Total Cart Value (₹): ");
        double cart_value = sc.nextDouble();

        System.out.print("Membership Years: ");
        int membershipYears = sc.nextInt();

        System.out.print("Item Count: ");
        int itemCount = sc.nextInt();

        System.out.print("Has Coupon? (true/false): ");
        boolean has_coupon = sc.nextBoolean();

        System.out.print("Is Weekend? (true/false): ");
        boolean is_weeknd = sc.nextBoolean();

        System.out.print("purchase_history (High/Medium/Low): ");
        String purchase_history = sc.next();

        String loyaltyDiscount = null;

        
        if (cart_value > 10000 &&
                 membershipYears >= 5) {
            loyaltyDiscount = "VIP Elite Reward - 20% off + gift Voucher";
        }

        else if (cart_value > 5000 &&
            membershipYears >= 2 &&
            itemCount > 0) {

            loyaltyDiscount = "Premium Bundle";
        }

        else if (itemCount > 8 ||
                 (cart_value > 3000 && is_weeknd)) {
             
            loyaltyDiscount = "Free Shipping + 10 % off";
        }

        else if (purchase_history.equalsIgnoreCase("High") &&
                 has_coupon) {

            loyaltyDiscount = "12% off + Shipping charges";
        }

        

        System.out.println("\nLoyalty Discount:" + loyaltyDiscount);
    }
}
