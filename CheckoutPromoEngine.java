import java.util.Scanner;

public class CheckoutPromoEngine {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Subtotal (₹): ");
        double subtotal = sc.nextDouble();

        System.out.print("Electronics Count: ");
        int elecCount = sc.nextInt();

        System.out.print("Minimum Electronics Price: ");
        double minElecPrice = sc.nextDouble();

        System.out.print("Clothing Count: ");
        int clothCount = sc.nextInt();

        System.out.print("Grocery Count: ");
        int groceryCount = sc.nextInt();

        int promotionFlags = 0;

        double freeItemsDiscount =
                (elecCount / 3) * minElecPrice;

        double bundleDiscount =
                (clothCount * 150) +
                (groceryCount * 20);

        double maxBundle = subtotal * 0.15;

        if (bundleDiscount > maxBundle)
            bundleDiscount = maxBundle;

        double flashDiscount = 0;

        if (subtotal > 10000) {
            flashDiscount = subtotal * 0.20;
            promotionFlags |= 1; // Bit 0
        

        double loyaltyDiscount = 0;

        if (elecCount > 0 &&
            clothCount > 0 &&
            groceryCount > 0) {

            loyaltyDiscount = subtotal * 0.05;
            promotionFlags |= 2; // Bit 1
        }

        double totalDiscount =
                freeItemsDiscount +
                bundleDiscount +
                flashDiscount +
                loyaltyDiscount;

        double finalAmount =
                subtotal - totalDiscount;

        System.out.println("\n===== BILL SUMMARY =====");

        System.out.println("Subtotal              : " + subtotal);
        System.out.println("Free Item Discount    : " + freeItemsDiscount);
        System.out.println("Bundle Discount       : " + bundleDiscount);
        System.out.println("Flash Sale Discount   : " + flashDiscount);
        System.out.println("Loyalty Discount      : " + loyaltyDiscount);

        System.out.println("Total Discount        : " + totalDiscount);
        System.out.println("Final Amount          : " + finalAmount);

        System.out.println("Promotion Flags       : " +
                promotionFlags +
                " (0b" +
                String.format("%2s",
                        Integer.toBinaryString(promotionFlags))
                        .replace(' ', '0') +
                ")");
    }
}
}