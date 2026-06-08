package FleetCore;

import java.util.Scanner;

class V {
    String brand;
    int speed;

    V(String brand, int speed) {
        this.brand = brand;
        this.speed = speed;
    }

    @SuppressWarnings("unused")
    String describe() {
        return "V " + brand + ": " + speed + " km/h";
    }
}

class Car extends V {
    int seats;

    Car(String brand, int speed, int seats) {
        super(brand, speed);
        this.seats = seats;
    }

    @Override
    String describe() {
        return "Car " + brand + ": " + speed + " km/h, " + seats + " seats";
    }
}

class Truck extends V {
    int cargo;

    Truck(String brand, int speed, int cargo) {
        super(brand, speed);
        this.cargo = cargo;
    }

    @Override
    String describe() {
        return "Truck " + brand + ": " + speed + " km/h, " + cargo + " kg cargo";
    }
}

public class level4 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String type = sc.next();

        if (type.equalsIgnoreCase("CAR")) {

            String brand = sc.next();
            int speed = sc.nextInt();
            int seats = sc.nextInt();

            Car car = new Car(brand, speed, seats);
            System.out.println(car.describe());

        } else if (type.equalsIgnoreCase("TRUCK")) {

            String brand = sc.next();
            int speed = sc.nextInt();
            int cargo = sc.nextInt();

            Truck truck = new Truck(brand, speed, cargo);
            System.out.println(truck.describe());
        }

        sc.close();
    }
}