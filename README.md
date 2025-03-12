# KtorSonOfKmer - Car Management API

This project is a RESTful API for managing car/automobile data, built with Ktor framework. It provides endpoints for retrieving and creating car information.

## Project Overview

KtorSonOfKmer is a demonstration project showcasing how to build a modern web API using Kotlin and Ktor. The application manages automobile data with fields like brand, model, license plate, registration year, mileage, status, and daily cost.

This project was created using the [Ktor Project Generator](https://start.ktor.io).

## Technologies Used

| Technology | Purpose |
|------------|---------|
| [Ktor](https://ktor.io/) | Lightweight web framework for Kotlin |
| [Exposed](https://github.com/JetBrains/Exposed) | SQL framework for database access |
| [H2 Database](https://www.h2database.com/) | In-memory database for development |
| [Koin](https://insert-koin.io/) | Dependency injection framework |
| [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) | JSON serialization/deserialization |
| [Swagger](https://swagger.io/) | API documentation |

## Project Structure

```
src/
├── main/
│   ├── kotlin/
│   │   ├── com/takaotech/
│   │   │   ├── Application.kt       # Main application entry point
│   │   │   ├── Databases.kt         # Database configuration
│   │   │   ├── Frameworks.kt        # Framework configuration (Koin)
│   │   │   ├── HTTP.kt              # HTTP configuration (Swagger)
│   │   │   └── Routing.kt           # Main routing configuration
│   │   ├── model/
│   │   │   └── AutoDTO.kt           # Data transfer objects
│   │   ├── repository/
│   │   │   └── AutoRepository.kt    # Data access layer
│   │   ├── routing/
│   │   │   └── AutoRouting.kt       # API endpoint definitions
│   │   └── tables/
│   │       └── Auto.kt              # Database table definitions
│   └── resources/
│       └── openapi/                 # API documentation
└── test/
    └── kotlin/                      # Test classes
```

## API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/auto` | GET | Retrieve all cars |
| `/auto/{id}` | GET | Retrieve a specific car by ID |
| `/auto/init` | GET | Initialize a new car with random data (for testing) |

## Setup and Installation

### Prerequisites

- JDK 11 or higher
- Gradle

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:

```bash
./gradlew run
```

The server will start on `http://0.0.0.0:8080`.

### API Documentation

Once the application is running, you can access the Swagger UI at:

```
http://localhost:8080/openapi
```

## Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
|-------------------------------|----------------------------------------------------------------------|
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

## Development Guidelines

### Code Style

- Follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful names for classes, functions, and variables
- Write comments for complex logic
- Keep functions small and focused on a single responsibility

### Project Structure Guidelines

- Place DTOs in the `model` package
- Place database entities in the `tables` package
- Place repositories in the `repository` package
- Place routing logic in the `routing` package

### Adding New Features

1. Define the data model in the `model` package
2. Create database tables in the `tables` package
3. Implement repository methods in the `repository` package
4. Define API endpoints in the `routing` package
5. Update the OpenAPI documentation

### Testing

- Write unit tests for repositories and services
- Write integration tests for API endpoints
- Run tests before submitting changes:

```bash
./gradlew test
```

## Useful Resources

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- [Exposed Documentation](https://github.com/JetBrains/Exposed/wiki)
- [Koin Documentation](https://insert-koin.io/docs/quickstart/kotlin)
