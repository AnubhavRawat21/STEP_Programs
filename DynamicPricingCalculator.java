import java.util.Scanner;

public class DynamicPricingCalculator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Base Price: ");
        double basePrice = sc.nextDouble();

        System.out.print("Current Customers: ");
        int currentCustomers = sc.nextInt();

        System.out.print("Store Capacity: ");
        int storeCapacity = sc.nextInt();

        System.out.print("Inventory: ");
        int inventory = sc.nextInt();

        System.out.print("Customer Loyalty Years: ");
        int loyaltyYears = sc.nextInt();

        System.out.print("Current Hour (0-23): ");
        int hour = sc.nextInt();

        // Traffic Factor
        double trafficRatio = (double) currentCustomers / storeCapacity;

        double trafficFactor = 1 + trafficRatio * 0.5;

        if (trafficRatio > 0.7) {
            trafficFactor *= 1.20;
        }

        // Inventory Factor
        double inventoryFactor =
                (inventory < 10) ? 1.15 :
                (inventory > 100) ? 0.95 :
                1.0;

        // Loyalty Discount
        double loyaltyDiscount = loyaltyYears * 2;

        if (loyaltyDiscount > 15) {
            loyaltyDiscount = 15;
        }

        // Hour Factor
        double hourFactor =
                (hour >= 19 || hour <= 6) ? 1.10 : 1.0;

        // Final Price
        double finalPrice =
                basePrice *
                trafficFactor *
                inventoryFactor *
                hourFactor;

        // Loyalty discount applied at end
        finalPrice = finalPrice * (1 - loyaltyDiscount / 100);

        // Output
        System.out.println("\n------ PRICE REPORT ------");
        System.out.println("Traffic Factor     : " + trafficFactor);
        System.out.println("Inventory Factor   : " + inventoryFactor);
        System.out.println("Hour Factor        : " + hourFactor);
        System.out.println("Loyalty Discount   : " + loyaltyDiscount + "%");
        System.out.printf("Final Price        : %.2f\n", finalPrice);

        sc.close();
    }
}