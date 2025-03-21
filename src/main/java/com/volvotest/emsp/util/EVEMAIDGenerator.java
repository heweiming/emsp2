package com.volvotest.emsp.util;

import java.util.ArrayList;
import java.util.Scanner;

class ElectricVehicle {
    private String vehicleId;
    private String manufacturer;
    private String model;
    private String emaid;

    public ElectricVehicle(String vehicleId, String manufacturer, String model) {
        this.vehicleId = vehicleId;
        this.manufacturer = manufacturer;
        this.model = model;
        this.emaid = generateEMAID(vehicleId, manufacturer, model);
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getEMAID() {
        return emaid;
    }

    // Method to generate EMAID
    private String generateEMAID(String vehicleId, String manufacturer, String model) {
        // Example EMAID format: COUNTRY-MANUFACTURER-MODEL-UNIQUEID-CHECK
        String countryCode = "US"; // Example: United States
        String manufacturerCode = manufacturer.substring(0, 3).toUpperCase(); // First 3 letters of manufacturer
        String modelCode = model.replaceAll("\\s", "").toUpperCase(); // Remove spaces and convert to uppercase
        String uniqueId = vehicleId.replaceAll("[^0-9]", ""); // Extract numbers from vehicleId
        String checkDigit = generateCheckDigit(uniqueId); // Simple check digit for demonstration
        return countryCode + "-" + manufacturerCode + "-" + modelCode + "-" + uniqueId + "-" + checkDigit;
    }

    // Method to generate a simple check digit (for demonstration purposes)
    private String generateCheckDigit(String uniqueId) {
        int sum = 0;
        for (char c : uniqueId.toCharArray()) {
            sum += Character.getNumericValue(c);
        }
        return String.valueOf(sum % 10); // Last digit of the sum
    }

    @Override
    public String toString() {
        return "ElectricVehicle{" +
                "vehicleId='" + vehicleId + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", emaid='" + emaid + '\'' +
                '}';
    }
}

public class EVEMAIDGenerator {
    private static ArrayList<ElectricVehicle> electricVehicles = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- EV EMAID Generator ---");
            System.out.println("1. Add Electric Vehicle");
            System.out.println("2. Display All Electric Vehicles with EMAIDs");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addElectricVehicle();
                    break;
                case 2:
                    displayAllVehicles();
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addElectricVehicle() {
        System.out.print("Enter Vehicle ID: ");
        String vehicleId = scanner.nextLine();
        System.out.print("Enter Manufacturer: ");
        String manufacturer = scanner.nextLine();
        System.out.print("Enter Model: ");
        String model = scanner.nextLine();

        ElectricVehicle ev = new ElectricVehicle(vehicleId, manufacturer, model);
        electricVehicles.add(ev);
        System.out.println("Electric Vehicle added successfully with EMAID: " + ev.getEMAID());
    }

    private static void displayAllVehicles() {
        if (electricVehicles.isEmpty()) {
            System.out.println("No electric vehicles available.");
        } else {
            System.out.println("\n--- List of Electric Vehicles with EMAIDs ---");
            for (ElectricVehicle ev : electricVehicles) {
                System.out.println(ev);
            }
        }
    }
}
