package com.wayne.airportCLI.service;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.Scanner;

import static org.mockito.Mockito.*;

class ApiServiceTest {

    private ApiService apiService;
    private Scanner mockScanner;

    @BeforeEach
    void setup() {
        apiService = new ApiService();
        mockScanner = mock(Scanner.class);
    }

    @Test
    void testGetAirportsByCity() throws Exception {
        when(mockScanner.nextLine()).thenReturn("1");

        try (MockedStatic<Request> mockedRequest = mockStatic(Request.class)) {
            var mockRequest = mock(Request.class);
            var mockExecutor = mock(Request.class);
            when(Request.get("http://localhost:8080/cities/1/airports")).thenReturn(mockRequest);
            when(mockRequest.execute()).thenReturn(mockExecutor);
            when(mockExecutor.returnContent()).thenReturn(() -> "[{\"name\":\"YYT\"}]");

            apiService.getAirportsByCity(mockScanner);
        }
    }

    @Test
    void testGetAircraftByPassenger() throws Exception {
        when(mockScanner.nextLine()).thenReturn("1");

        try (MockedStatic<Request> mockedRequest = mockStatic(Request.class)) {
            var mockRequest = mock(Request.class);
            var mockExecutor = mock(Request.class);
            when(Request.get("http://localhost:8080/passengers/1/aircraft")).thenReturn(mockRequest);
            when(mockRequest.execute()).thenReturn(mockExecutor);
            when(mockExecutor.returnContent()).thenReturn(() -> "[{\"type\":\"Boeing 737\"}]");

            apiService.getAircraftByPassenger(mockScanner);
        }
    }

    @Test
    void testGetPassengersByAirport() throws Exception {
        when(mockScanner.nextLine()).thenReturn("1");

        try (MockedStatic<Request> mockedRequest = mockStatic(Request.class)) {
            var mockRequest = mock(Request.class);
            var mockExecutor = mock(Request.class);
            when(Request.get("http://localhost:8080/airports/1/passengers")).thenReturn(mockRequest);
            when(mockRequest.execute()).thenReturn(mockExecutor);
            when(mockExecutor.returnContent()).thenReturn(() -> "[{\"firstName\":\"Wayne\"}]");

            apiService.getPassengersByAirport(mockScanner);
        }
    }

    @Test
    void testGetFlightsBetweenAirports() throws Exception {
        when(mockScanner.nextLine()).thenReturn("1", "2");

        try (MockedStatic<Request> mockedRequest = mockStatic(Request.class)) {
            var mockRequest = mock(Request.class);
            var mockExecutor = mock(Request.class);
            String expectedUrl = "http://localhost:8080/flights?origin=1&destination=2";
            when(Request.get(expectedUrl)).thenReturn(mockRequest);
            when(mockRequest.execute()).thenReturn(mockExecutor);
            when(mockExecutor.returnContent()).thenReturn(() -> "[{\"flightNumber\":\"AC123\"}]");

            apiService.getFlightsBetweenAirports(mockScanner);
        }
    }
}
