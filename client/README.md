# KtorSonOfKmer Client Module

This module provides a Kotlin client for the KtorSonOfKmer API using Ktorfit, a Retrofit-inspired HTTP client for
Kotlin.

## Features

- Type-safe API client for KtorSonOfKmer
- Support for all API endpoints defined in the OpenAPI specification
- Automatic serialization/deserialization of request/response bodies
- Support for authentication

## Usage

### Basic Usage

```kotlin
import com.takaotech.client.KtorFitClient
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val client = KtorFitClient("http://localhost:8080")

    try {
        // Get all autos
        val autos = client.autoApi.getAllAuto()
        println("Found ${autos.size} autos")

        // Get a specific auto
        val auto = client.autoApi.getAutoById(1)
        println("Auto details: ${auto.marca} ${auto.modello}, Targa: ${auto.targa}")

        // Get all clients
        val clienti = client.clienteApi.getAllClienti()
        println("Found ${clienti.size} clienti")

        // Echo a text message
        val echoResponse = client.echoApi.echoText("Hello, Ktorfit!")
        println("Echo response: $echoResponse")
    } finally {
        client.close()
    }
}
```

### Authentication

For endpoints that require authentication (like the Noleggio API), you need to provide an Authorization header:

```kotlin
import java.util.Base64

// Basic authentication with username:password
val auth = "Basic " + Base64.getEncoder().encodeToString("admin:admin".toByteArray())
val noleggi = client.noleggioApi.getAllNoleggi(auth)
```

## API Interfaces

The client provides the following API interfaces:

- `AutoApi` - For interacting with car-related endpoints
- `ClienteApi` - For interacting with customer-related endpoints
- `NoleggioApi` - For interacting with rental-related endpoints (requires authentication)
- `EchoApi` - For interacting with echo endpoints (for testing)

## Running the Example

To run the example application:

```bash
./gradlew :client:run
```

## Dependencies

- Kotlin Coroutines
- Ktorfit
- Ktor Client
- Kotlinx Serialization