package com.wayne.airportCLI.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ApiServiceTest {

    private ApiService apiService;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        apiService = new ApiService();

        // Redirect console output for testing
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testGetAirportsByCity_withMockInput() {
        Scanner scanner = new Scanner("1\n");
        apiService.getAirportsByCity(scanner);
        String output = outContent.toString();
        assertTrue(output.contains("→ Fetching airports for city ID 1"));
    }

    @Test
    void testGetAircraftByPassenger_withMockInput() {
        Scanner scanner = new Scanner("1\n");
        apiService.getAircraftByPassenger(scanner);
        String output = outContent.toString();
        assertTrue(output.contains("→ Fetching aircraft for passenger ID 1"));
    }

    @Test
    void testGetPassengersByAirport_withMockInput() {
        Scanner scanner = new Scanner("1\n");
        apiService.getPassengersByAirport(scanner);
        String output = outContent.toString();
        assertTrue(output.contains("→ Fetching passengers for airport ID 1"));
    }

    @Test
    void testGetFlightsBetweenAirports_withMockInput() {
        Scanner scanner = new Scanner("1\n2\n");
        apiService.getFlightsBetweenAirports(scanner);
        String output = outContent.toString();
        assertTrue(output.contains("→ Fetching flights from 1 to 2"));
    }
}
