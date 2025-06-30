package com.wayne.airportCLI.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ApiService {

    private static final String BASE_URL = "http://localhost:8080";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    // ---------------- GET REQUESTS ---------------- //

    public void getAirportsByCity(Scanner scanner) {
        System.out.print("Enter city ID: ");
        String cityId = scanner.nextLine();
        String url = BASE_URL + "/cities/" + cityId + "/airports";
        sendGet(url, "\n✈ Airports:");
    }

    public void getAircraftByPassenger(Scanner scanner) {
        System.out.print("Enter passenger ID: ");
        String id = scanner.nextLine();
        String url = BASE_URL + "/passengers/" + id + "/aircraft";
        sendGet(url, "\n🛩 Aircraft:");
    }

    public void getPassengersByAirport(Scanner scanner) {
        System.out.print("Enter airport ID: ");
        String airportId = scanner.nextLine();
        String url = BASE_URL + "/airports/" + airportId + "/passengers";
        sendGet(url, "\n👥 Passengers:");
    }

    public void getFlightsBetweenAirports(Scanner scanner) {
        System.out.print("Enter origin airport ID: ");
        String origin = scanner.nextLine();
        System.out.print("Enter destination airport ID: ");
        String destination = scanner.nextLine();
        String url = BASE_URL + "/flights?origin=" + origin + "&destination=" + destination;
        sendGet(url, "\n🛫 Flights:");
    }

    // ---------------- POST REQUESTS ---------------- //

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

        sendPost("/cities", json, "✅ City created:");
    }

    public void createAirport(Scanner scanner) {
        System.out.print("Airport name: ");
        String name = scanner.nextLine();
        System.out.print("Airport code (e.g. YYT): ");
        String code = scanner.nextLine().toUpperCase();
        System.out.print("City ID: ");
        String cityId = scanner.nextLine();

        String json = String.format("""
        {
            "name": "%s",
            "code": "%s",
            "city": { "id": %s }
        }
        """, name, code, cityId);

        sendPost("/airports", json, "✅ Airport created:");
    }

    public void createPassenger(Scanner scanner) {
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Phone number: ");
        String phone = scanner.nextLine();
        System.out.print("City ID: ");
        String cityId = scanner.nextLine();

        String json = String.format("""
        {
            "firstName": "%s",
            "lastName": "%s",
            "phoneNumber": "%s",
            "city": { "id": %s }
        }
        """, firstName, lastName, phone, cityId);

        sendPost("/passengers", json, "✅ Passenger created:");
    }

    // ---------------- SHARED METHODS ---------------- //

    private void sendGet(String url, String successHeader) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        sendRequest(request, successHeader);
    }

    private void sendPost(String endpoint, String json, String successHeader) {
        try {
            URI uri = URI.create(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            try (var os = conn.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            System.out.println("Status: " + status);

            try (var is = (status >= 200 && status < 300) ? conn.getInputStream() : conn.getErrorStream();
                 var reader = new BufferedReader(new InputStreamReader(is))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line.trim());
                }
                System.out.println(successHeader + "\n" + response);
            }

            conn.disconnect();
        } catch (IOException e) {
            System.err.println("❌ Request failed: " + e.getMessage());
        }
    }

    private void sendRequest(HttpRequest request, String successHeader) {
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + response.statusCode());
            System.out.println(successHeader + "\n" + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Request failed: " + e.getMessage());
        }
    }
}
