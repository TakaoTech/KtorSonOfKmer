# Contributing to KtorSonOfKmer

Thank you for considering contributing to KtorSonOfKmer! This document provides guidelines and instructions for contributing to this project.

## Project Overview

KtorSonOfKmer is a RESTful API for managing car/automobile data, built with Ktor framework. It provides endpoints for retrieving and creating car information. The application manages automobile data with fields like brand, model, license plate, registration year, mileage, status, and daily cost.

### Technologies Used

| Technology | Purpose |
|------------|---------|
| [Ktor](https://ktor.io/) | Lightweight web framework for Kotlin |
| [Exposed](https://github.com/JetBrains/Exposed) | SQL framework for database access |
| [H2 Database](https://www.h2database.com/) | In-memory database for development |
| [Koin](https://insert-koin.io/) | Dependency injection framework |
| [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) | JSON serialization/deserialization |
| [Swagger](https://swagger.io/) | API documentation |

## Code of Conduct

Please be respectful and considerate of others when contributing to this project.

## How to Contribute

### Reporting Bugs

If you find a bug, please create an issue with the following information:

- A clear, descriptive title
- Steps to reproduce the bug
- Expected behavior
- Actual behavior
- Any relevant logs or screenshots

### Suggesting Enhancements

If you have an idea for an enhancement, please create an issue with the following information:

- A clear, descriptive title
- A detailed description of the enhancement
- Any relevant examples or mockups

### Pull Requests

1. Fork the repository
2. Create a new branch for your feature or bugfix
3. Make your changes
4. Run tests to ensure your changes don't break existing functionality
5. Submit a pull request

## Development Workflow

1. Clone the repository
2. Create a new branch for your changes
3. Make your changes
4. Run tests to ensure your changes don't break existing functionality
5. Commit your changes with a clear, descriptive commit message
6. Push your changes to your fork
7. Submit a pull request

## Coding Standards

Please follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html) when contributing to this project.

### Code Style Guidelines

- Use meaningful names for classes, functions, and variables
- Write comments for complex logic
- Keep functions small and focused on a single responsibility
- Use proper indentation (4 spaces)
- Use camelCase for variables and functions, PascalCase for classes
- Add appropriate documentation for public APIs

## Testing

- Write unit tests for repositories and services
- Write integration tests for API endpoints
- Run tests before submitting changes:

```bash
./gradlew test
```

## Project Structure Guidelines

- Place DTOs in the `model` package
- Place database entities in the `tables` package
- Place repositories in the `repository` package
- Place routing logic in the `routing` package

## Adding New Features

1. Define the data model in the `model` package
2. Create database tables in the `tables` package
3. Implement repository methods in the `repository` package
4. Define API endpoints in the `routing` package
5. Update the OpenAPI documentation

## License

By contributing to this project, you agree that your contributions will be licensed under the same license as the project.
