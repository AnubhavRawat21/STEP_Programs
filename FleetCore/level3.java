package FleetCore;

import java.util.*;

class Vehi {
    String brand;
    int topSpeed;

    Vehi(String brand, int topSpeed) {
        this.brand = brand;
        this.topSpeed = topSpeed;
    }

    Vehi(String brand) {
        this.brand = brand;
        this.topSpeed = 80;
    }

    void display() {
        System.out.println(brand + ": " + topSpeed);
    }
}

public class level3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();

        Vehi[] Vehis = new Vehi[n];
        int defaultCount = 0;

        for (int i = 0; i < n; i++) {

            String type = sc.next();

            if (type.equals("FULL")) {
                String brand = sc.next();
                int speed = sc.nextInt();

                Vehis[i] = new Vehi(brand, speed);

            } else if (type.equals("DEFAULT")) {
                String brand = sc.next();

                Vehis[i] = new Vehi(brand);
                defaultCount++;
            }
        }

        for (Vehi v : Vehis) {
            v.display();
        }

        System.out.println("Default Constructors Used: " + defaultCount);

        sc.close();
    }
}