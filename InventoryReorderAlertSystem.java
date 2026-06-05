import java.util.Scanner;

public class InventoryReorderAlertSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Product ID: ");
        String productId = sc.nextLine();

        System.out.print("Current Stock: ");
        int stock = sc.nextInt();

        System.out.print("Safety Stock: ");
        int safetyStock = sc.nextInt();

        System.out.print("Daily Sales Rate: ");
        int dailySalesRate = sc.nextInt();

        System.out.print("Lead Time Days: ");
        int leadTimeDays = sc.nextInt();

        System.out.print("Seasonal Product? (true/false): ");
        boolean isSeasonal = sc.nextBoolean();

        System.out.print("Discount Active? (true/false): ");
        boolean discountActive = sc.nextBoolean();

        boolean needsReorder =
                (stock <= safetyStock) ||
                (isSeasonal && stock <= dailySalesRate * 3) ||
                (discountActive && stock <= 20);

        boolean urgentReorder =
                (stock <= dailySalesRate * 2) &&
                (leadTimeDays > 3 || isSeasonal) &&
                !(discountActive && stock > 10);

        int reorderQuantity =
                (int) (Math.max(stock * 0.5,
                        dailySalesRate * leadTimeDays)
                        * (isSeasonal ? 2 : 1));

        // Bitwise Urgency Encoding
        // Bit 0 : stock < 10
        // Bit 1 : seasonal
        // Bit 2 : discount
        // Bit 3 : leadTime > 5

        int urgencyCode = 0;

        if (stock < 10)
            urgencyCode |= (1 << 0);

        if (isSeasonal)
            urgencyCode |= (1 << 1);

        if (discountActive)
            urgencyCode |= (1 << 2);

        if (leadTimeDays > 5)
            urgencyCode |= (1 << 3);

        boolean priorityShipping =
                isSeasonal ^ discountActive;

        String action;

        if (urgentReorder) {
            action = "URGENT! Reorder";
        } else if (needsReorder) {
            action = "Reorder";
        } else if (urgencyCode > 0) {
            action = "Monitor Closely";
        } else {
            action = "No Action Required";
        }

        System.out.println("\n========== INVENTORY REPORT ==========");
        System.out.println("Product ID       : " + productId);
        System.out.println("Current Stock    : " + stock);
        System.out.println("Daily Sales      : " + dailySalesRate);
        System.out.println("Lead Time Days   : " + leadTimeDays);
        System.out.println("Seasonal         : " + isSeasonal);
        System.out.println("Discount Active  : " + discountActive);

        System.out.println("\nNeeds Reorder    : " + needsReorder);
        System.out.println("Urgent Reorder   : " + urgentReorder);

        System.out.println("Reorder Quantity : " + reorderQuantity);

        System.out.println("Urgency Code     : "
                + urgencyCode
                + " (0b"
                + String.format("%4s",
                Integer.toBinaryString(urgencyCode))
                .replace(' ', '0')
                + ")");

        System.out.println("Priority Shipping: "
                + (priorityShipping ? "YES" : "NO"));

        System.out.println("Expected Action  : " + action);

        sc.close();
    }
}