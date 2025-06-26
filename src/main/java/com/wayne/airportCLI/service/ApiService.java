package com.wayne.airportCLI.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.io.IOException;


public class ApiService {

    private static final String BASE_URL = "http://localhost:8080";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void getAirportsByCity(Scanner scanner) {
        System.out.print("Enter city ID: ");
        String cityId = scanner.nextLine();

        String url = BASE_URL + "/cities/" + cityId + "/airports";
        System.out.println("→ Fetching airports for city ID " + cityId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("\n🛬 Airports:\n" + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Failed to fetch airports: " + e.getMessage());
        }
    }


    public void getAircraftByPassenger(Scanner scanner) {
        System.out.print("Enter passenger ID: ");
        String id = scanner.nextLine();

        String url = BASE_URL + "/passengers/" + id + "/aircraft";
        System.out.println("→ Fetching aircraft for passenger ID " + id);

        try {
            String response = Request.get(url)
                    .execute()
                    .returnContent()
                    .asString();

            System.out.println("\n✈️ Aircraft:\n" + response);
        } catch (IOException e) {
            System.err.println("❌ Failed to fetch aircraft: " + e.getMessage());
        }
    }

    public void getPassengersByAirport(Scanner scanner) {
        System.out.print("Enter airport ID: ");
        String airportId = scanner.nextLine();

        String url = BASE_URL + "/airports/" + airportId + "/passengers";
        System.out.println("→ Fetching passengers for airport ID " + airportId);

        try {
            String response = Request.get(url)
                    .execute()
                    .returnContent()
                    .asString();

            System.out.println("\n👥 Passengers:\n" + response);
        } catch (IOException e) {
            System.err.println("❌ Failed to fetch passengers: " + e.getMessage());
        }
    }

    public void getFlightsBetweenAirports(Scanner scanner) {
        System.out.print("Enter origin airport ID: ");
        String origin = scanner.nextLine();
        System.out.print("Enter destination airport ID: ");
        String destination = scanner.nextLine();

        String url = BASE_URL + "/flights?origin=" + origin + "&destination=" + destination;
        System.out.println("→ Fetching flights from " + origin + " to " + destination);

        try {
            String response = Request.get(url)
                    .execute()
                    .returnContent()
                    .asString();

            System.out.println("\n🛫 Flights:\n" + response);
        } catch (IOException e) {
            System.err.println("❌ Failed to fetch flights: " + e.getMessage());
        }
    }

    public void showAdminMenu(Scanner scanner) {
        boolean editing = true;
        while (editing) {
            System.out.println("""
        ✏️ Admin Menu:
        1. Add new city
        2. Add new airport
        3. Add new passenger
        4. Return to main menu
        """);

            System.out.print("Your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> createCity(scanner);
                case "2" -> createAirport(scanner);
                case "3" -> createPassenger(scanner);
                case "4" -> editing = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public void createCity(Scanner scanner) {
        System.out.print("City name: ");
        String name = scanner.nextLine();
        System.out.print("State: ");
        String state = scanner.nextLine();
        System.out.print("Population: ");
        String pop = scanner.nextLine();

        String json = String.format("""
        {
            "name": "%s",
            "state": "%s",
            "population": %s
        }
        """, name, state, pop);

        try {
            String response = Request.post(BASE_URL + "/cities")
                    .bodyString(json, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            System.out.println("✅ City created: " + response);
        } catch (IOException e) {
            System.err.println("❌ Failed to create city: " + e.getMessage());
        }
    }

    public void createAirport(Scanner scanner) {
        System.out.print("Airport name: ");
        String name = scanner.nextLine();
        System.out.print("Airport code (e.g. YYT): ");
        String code = scanner.nextLine().toUpperCase();

        System.out.print("Enter city ID to link this airport to: ");
        String cityId = scanner.nextLine();

        String json = String.format("""
        {
            "name": "%s",
            "code": "%s",
            "city": { "id": %s }
        }
        """, name, code, cityId);

        try {
            String response = Request.post(BASE_URL + "/airports")
                    .bodyString(json, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            System.out.println("✅ Airport created:\n" + response);
        } catch (IOException e) {
            System.err.println("❌ Failed to create airport: " + e.getMessage());
        }
    }

    public void createPassenger(Scanner scanner) {
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Phone number: ");
        String phone = scanner.nextLine();

        System.out.print("City ID (home city): ");
        String cityId = scanner.nextLine();

        String json = String.format("""
        {
            "firstName": "%s",
            "lastName": "%s",
            "phoneNumber": "%s",
            "city": { "id": %s }
        }
        """, firstName, lastName, phone, cityId);

        try {
            String response = Request.post(BASE_URL + "/passengers")
                    .bodyString(json, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            System.out.println("✅ Passenger created:\n" + response);
        } catch (IOException e) {
            System.err.println("❌ Failed to create passenger: " + e.getMessage());
        }
    }




}
