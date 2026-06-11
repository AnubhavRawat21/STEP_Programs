import java.io.Serial;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        int a= calculator.add(1, 2);
        int b= calculator.sub(1, 2);
        System.out.println(a);
        System.out.println(b);
    }
}