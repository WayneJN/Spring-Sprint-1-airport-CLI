package com.wayne.airportCLI;

import com.wayne.airportCLI.service.ApiService;

import java.util.Scanner;

public class AirportCliApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ApiService apiService = new ApiService();

        System.out.println("\n🛬 Welcome to the Airport Information CLI 🛫");
        System.out.println("Connecting to backend API...\n");

        boolean running = true;
        while (running) {
            System.out.println("""
                🛠️ Please select an option:
                1. List all airports in a city
                2. Show all aircraft flown by a passenger
                3. Show all passengers who have used an airport
                4. Show all flights between two airports
                5. Exit
                6. Admin Mode
                
                """);

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> apiService.getAirportsByCity(scanner);
                case "2" -> apiService.getAircraftByPassenger(scanner);
                case "3" -> apiService.getPassengersByAirport(scanner);
                case "4" -> apiService.getFlightsBetweenAirports(scanner);
                case "5" -> {
                    System.out.println("\nGoodbye! ✈️");
                    running = false;
                }
                case "6" -> {
                    System.out.print("Enter admin PIN: ");
                    String pin = scanner.nextLine();
                    if (pin.equals("1234")) {
                        apiService.showAdminMenu(scanner);
                    } else {
                        System.out.println("❌ Invalid PIN.\n");
                    }
                }

                default -> System.out.println("Invalid choice. Please enter 1–5.\n");
            }
        }

        scanner.close();
    }
}
