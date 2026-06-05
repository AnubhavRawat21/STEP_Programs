package FleetCore;

import java.util.Scanner;

abstract class Vehicle1 {
    String brand;

    Vehicle1(String brand) {
        this.brand = brand;
    }

    abstract int calculateFare(int distance);

    String getBrand() {
        return brand;
    }
}

class car1 extends Vehicle1 {

    car1(String brand) {
        super(brand);
    }

    @Override
    int calculateFare(int distance) {
        return 10 * distance;
    }
}

class Bike1 extends Vehicle1 {

    Bike1(String brand) {
        super(brand);
    }

    @Override
    int calculateFare(int distance) {
        return 5 * distance;
    }
}

class Truck1 extends Vehicle1 {

    Truck1(String brand) {
        super(brand);
    }

    @Override
    int calculateFare(int distance) {
        return 15 * distance + 100;
    }
}

public class level7 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        Vehicle1[] fleet = new Vehicle1[n];

        for (int i = 0; i < n; i++) {

            String type = sc.next();
            String brand = sc.next();

            if (type.equalsIgnoreCase("car")) {
                fleet[i] = new car1(brand);
            }
            else if (type.equalsIgnoreCase("bike")) {
                fleet[i] = new Bike1(brand);
            }
            else if (type.equalsIgnoreCase("truck")) {
                fleet[i] = new Truck1(brand);
            }
        }
        System.out.println(java.util.Arrays.toString(fleet));
        int distance = 20;

        int totalFare = 0;

        int highestFare = -1;
        String highestVehicle1 = "";

        int car1Count = 0;
        int Bike1Count = 0;
        int Truck1Count = 0;

        for (Vehicle1 v : fleet) {

            int fare = v.calculateFare(distance);

            totalFare += fare;

            // Keep first Vehicle1 in case of tie
            if (fare > highestFare) {
                highestFare = fare;
                highestVehicle1 = v.getBrand();
            }

            if (v instanceof car1) {
                car1Count++;
            }
            else if (v instanceof Bike1) {
                Bike1Count++;
            }
            else if (v instanceof Truck1) {
                Truck1Count++;
            }
        }

        System.out.println("Total Fare: " + totalFare);
        System.out.println("Highest Fare: " +
                highestVehicle1 + " (" + highestFare + ")");
        System.out.println("cars: " + car1Count);
        System.out.println("Bikes: " + Bike1Count);
        System.out.println("Trucks: " + Truck1Count);

        sc.close();
    }
}