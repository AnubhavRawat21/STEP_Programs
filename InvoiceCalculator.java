public class InvoiceCalculator {
public static void main(String[] args) {
String productName = "Wireless Keyboard";
int quantity = 3;
double unitPrice = 1499.99;
final double TAX_RATE = 0.18;
final float DISCOUNT_RATE = 0.12f;
final String Currency_symbol = "$";
double subtotal = quantity * unitPrice;
double discountAmt = subtotal * DISCOUNT_RATE;
double afterDiscount = subtotal - discountAmt;
double taxAmount = afterDiscount * TAX_RATE;
double totalAmount = afterDiscount + taxAmount;
System.out.println("Product: " + productName);
System.out.println("Discount:" + discountAmt);
System.out.printf("Subtotal: %.2f%n", subtotal);

System.out.printf("Discount (raw): %.2f%n", discountAmt);
System.out.printf("GST(18%%): %.2f%n", taxAmount);
System.out.printf("TOTAL: %s%.2f%n", Currency_symbol, totalAmount);
}
}