# 🛬 Airport CLI Application

A command-line interface (CLI) Java application that connects to a REST API to query cities, airports, passengers, and aircraft. This CLI is the frontend counterpart to the Spring Boot API hosted in a separate repository.

---

## 📦 Features

This CLI answers the following questions via API:

1. What airports are there in each city?
2. What aircraft has each passenger flown on?
3. What passengers have used a specific airport?
4. What flights are available between two airports?

Optional admin mode allows creation of new cities, airports, and passengers.

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven
- The backend API running at `http://localhost:8080`
  - See: [airportAPI GitHub Repository](https://github.com/WayneJN/Spring-Sprint-1-airport-API)


### Run the CLI

- bash
- ./mvnw compile exec:java -Dexec.mainClass="com.wayne.airportCLI.AirportCliApplication"

## 🧪 Testing

This project includes automated unit tests written with JUnit 5. All user input is mocked using Scanner to verify behavior, and System.out is captured for assertions.

- bash
- ./mvnw test

### GitHub Actions

GitHub Actions are configured to automatically run these tests on all PRs and commits to main.

## 🛠 Technologies

- Java 17
- Maven
- JUnit 5
- GitHub Actions
- Java HttpClient

## 🔗 Related Projects
[🧠 Backend API: airportAPI GitHub Repository](https://github.com/WayneJN/Spring-Sprint-1-airport-API)

## 👥 Team
Wayne N.
