package FleetCore;

import java.util.Scanner;

abstract class Vehicle {
    String brand;

    Vehicle(String brand) {
        this.brand = brand;
    }

    abstract int calculateFare(int distance);

    abstract String getType();
}

class Car extends Vehicle {

    Car(String brand) {
        super(brand);
    }

    @Override
    int calculateFare(int distance) {
        return 10 * distance;
    }

    @Override
    String getType() {
        return "Car";
    }
}

class Bike extends Vehicle {

    Bike(String brand) {
        super(brand);
    }

    @Override
    int calculateFare(int distance) {
        return 5 * distance;
    }

    @Override
    String getType() {
        return "Bike";
    }
}

class Truck extends Vehicle {

    Truck(String brand) {
        super(brand);
    }

    @Override
    int calculateFare(int distance) {
        return 15 * distance + 100;
    }

    @Override
    String getType() {
        return "Truck";
    }
}

class VehicleFactory {

    static Vehicle createVehicle(String type, String brand) {

        if (type.equalsIgnoreCase("CAR")) {
            return new Car(brand);
        }

        if (type.equalsIgnoreCase("BIKE")) {
            return new Bike(brand);
        }

        if (type.equalsIgnoreCase("TRUCK")) {
            return new Truck(brand);
        }

        return null;
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        
        String type = sc.next();
        String brand = sc.next();
        
        int distance = sc.nextInt();

        Vehicle vehicle =
                VehicleFactory.createVehicle(type, brand);

        System.out.println(
                brand + " (" +
                vehicle.getType() +
                ") Fare: " +
                vehicle.calculateFare(distance)
        );

        sc.close();
    }
}