import java.util.Scanner;

public class SmartPricingEngine {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Product Category: ");
        String category = sc.nextLine();

        System.out.print("Base Price: ");
        double basePrice = sc.nextDouble();

        System.out.print("Quantity: ");
        int quantity = sc.nextInt();

        sc.nextLine();

        System.out.print("Customer Type (Regular/Premium/VIP): ");
        String customerType = sc.nextLine();

        double taxRate = 0;
        double customerDiscount = 0;
        double bulkDiscount = 0;

        if(category.equalsIgnoreCase("Electronics"))
            taxRate = 18;
        else if(category.equalsIgnoreCase("Apparel"))
            taxRate = 12;
        else if(category.equalsIgnoreCase("Grocery"))
            taxRate = 5;
        else if(category.equalsIgnoreCase("Beauty"))
            taxRate = 15;

        if(customerType.equalsIgnoreCase("Premium"))
            customerDiscount = 8;
        else if(customerType.equalsIgnoreCase("VIP"))
            customerDiscount = 15;

        // Bulk Discount
        if(quantity > 10)
            bulkDiscount = 5;

        double totalDiscount =customerDiscount + bulkDiscount;

        double discountedPrice =basePrice * (1 - totalDiscount / 100);

        // Price Floor
        double priceFloor = basePrice * 0.60;

        if(discountedPrice < priceFloor)
            discountedPrice = priceFloor;

        // Apply Tax
        double finalPrice =
                discountedPrice * (1 + taxRate / 100);

        System.out.println("\n----- BILL SUMMARY -----");
        System.out.println("Base Price      : ₹" + basePrice);
        System.out.println("Tax Rate        : " + taxRate + "%");
        System.out.println("Customer Disc.  : " + customerDiscount + "%");
        System.out.println("Bulk Discount   : " + bulkDiscount + "%");
        System.out.println("Price Floor     : ₹" + priceFloor);
        System.out.printf("Final Price     : ₹%.2f\n", finalPrice);

        sc.close();
    }
}