package com.takaotech.routing

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.get
import model.ClienteCreateDTO
import org.koin.ktor.ext.get
import repository.ClienteRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Routing.clienteRouting() {
    route("/cliente") {
        get {
            val repository = get<ClienteRepository>()
            call.respond(repository.getAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val repository = get<ClienteRepository>()
            val cliente = id?.let { repository.getById(it) }
            if (cliente != null) call.respond(cliente) else call.respond(HttpStatusCode.NotFound)
        }

        post {
            val repository = get<ClienteRepository>()
            val clienteCreateDTO = call.receive<ClienteCreateDTO>()
            val cliente = repository.add(clienteCreateDTO)
            call.respond(HttpStatusCode.Created)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val repository = get<ClienteRepository>()
                repository.delete(id)
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
            }
        }

        get("/init") {
            val repository = get<ClienteRepository>()
            val futureDate = LocalDate.now().plusYears(5)
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE

            repository.add(
                ClienteCreateDTO(
                    nome = "Mario",
                    cognome = "Rossi",
                    email = "mario.rossi@example.com",
                    telefono = "+39 123 456 7890",
                    indirizzo = "Via Roma 123, 00100 Roma",
                    patenteNumero = "AB123456",
                    dataScadenzaPatente = futureDate.format(formatter)
                )
            )

            call.respond(HttpStatusCode.Created)
        }

        get("/search/{term}/*") {
            val searchTerm = call.parameters["term"] ?: ""
            val repository = get<ClienteRepository>()
            val allClients = repository.getAll()

            // Filter clients based on the search term
            val filteredClients = allClients.filter { cliente ->
                cliente.nome.contains(searchTerm, ignoreCase = true) ||
                        cliente.cognome.contains(searchTerm, ignoreCase = true) ||
                        cliente.email.contains(searchTerm, ignoreCase = true) ||
                        cliente.telefono.contains(searchTerm, ignoreCase = true) ||
                        cliente.indirizzo.contains(searchTerm, ignoreCase = true) ||
                        cliente.patenteNumero.contains(searchTerm, ignoreCase = true)
            }

            call.respond(filteredClients)
        }

        // More structured wildcard example
        // Examples:
        // /cliente/filter/nome/mario/cognome/rossi - Finds clients with name containing "mario" AND surname containing "rossi"
        // /cliente/filter/email/example.com - Finds clients with email containing "example.com"
        // /cliente/filter/patente/AB123/indirizzo/roma - Finds clients with license number containing "AB123" AND address containing "roma"
        get("/filter/{field}/{value}/**") {
            val field = call.parameters["field"] ?: ""
            val value = call.parameters["value"] ?: ""
            val repository = get<ClienteRepository>()
            val allClients = repository.getAll()

            // Get all remaining path parameters for additional filters
            val remainingPath = call.request.path().substringAfter("/filter/$field/$value/")
            val additionalFilters = if (remainingPath.isNotEmpty()) {
                remainingPath.split("/").chunked(2).filter { it.size == 2 }
                    .map { it[0] to it[1] }
            } else {
                emptyList()
            }

            // Apply the first filter
            var filteredClients = when (field.lowercase()) {
                "nome" -> allClients.filter { it.nome.contains(value, ignoreCase = true) }
                "cognome" -> allClients.filter { it.cognome.contains(value, ignoreCase = true) }
                "email" -> allClients.filter { it.email.contains(value, ignoreCase = true) }
                "telefono" -> allClients.filter { it.telefono.contains(value, ignoreCase = true) }
                "indirizzo" -> allClients.filter { it.indirizzo.contains(value, ignoreCase = true) }
                "patente" -> allClients.filter { it.patenteNumero.contains(value, ignoreCase = true) }
                else -> allClients
            }

            // Apply additional filters
            for ((additionalField, additionalValue) in additionalFilters) {
                filteredClients = when (additionalField.lowercase()) {
                    "nome" -> filteredClients.filter { it.nome.contains(additionalValue, ignoreCase = true) }
                    "cognome" -> filteredClients.filter { it.cognome.contains(additionalValue, ignoreCase = true) }
                    "email" -> filteredClients.filter { it.email.contains(additionalValue, ignoreCase = true) }
                    "telefono" -> filteredClients.filter { it.telefono.contains(additionalValue, ignoreCase = true) }
                    "indirizzo" -> filteredClients.filter { it.indirizzo.contains(additionalValue, ignoreCase = true) }
                    "patente" -> filteredClients.filter {
                        it.patenteNumero.contains(
                            additionalValue,
                            ignoreCase = true
                        )
                    }

                    else -> filteredClients
                }
            }

            call.respond(filteredClients)
        }

        // Example using regex path
        // Examples:
        // /cliente/patente/AB123456 - Finds clients with license number matching the pattern (2 letters followed by 6 digits)
        // /cliente/patente/CD789012 - Finds clients with license number matching the pattern
        get("/patente/{patente:([A-Z]{2}\\d{6})}") {
            val patenteNumero = call.parameters["patente"] ?: ""
            val repository = get<ClienteRepository>()
            val allClients = repository.getAll()

            // Find clients with exact license number match
            val matchingClients = allClients.filter {
                it.patenteNumero.equals(patenteNumero, ignoreCase = true)
            }

            call.respond(matchingClients)
        }

        // Example using tailcard
        // Examples:
        // /cliente/advanced-search/nome/mario - Search by name
        // /cliente/advanced-search/nome/mario/cognome/rossi - Search by name AND surname
        // /cliente/advanced-search/email/example.com/patente/AB123 - Search by email AND license
        get("/advanced-search/{...}") {
            val repository = get<ClienteRepository>()
            val allClients = repository.getAll()

            // Get the tail part of the URL (everything after /advanced-search/)
            val tailPart = call.request.path().substringAfter("/advanced-search/")

            // Parse the tail part into field-value pairs
            val searchCriteria = tailPart.split("/").chunked(2).filter { it.size == 2 }
                .map { it[0] to it[1] }

            // Start with all clients and apply filters sequentially
            var filteredClients = allClients

            // Apply each search criterion
            for ((field, value) in searchCriteria) {
                filteredClients = when (field.lowercase()) {
                    "nome" -> filteredClients.filter { it.nome.contains(value, ignoreCase = true) }
                    "cognome" -> filteredClients.filter { it.cognome.contains(value, ignoreCase = true) }
                    "email" -> filteredClients.filter { it.email.contains(value, ignoreCase = true) }
                    "telefono" -> filteredClients.filter { it.telefono.contains(value, ignoreCase = true) }
                    "indirizzo" -> filteredClients.filter { it.indirizzo.contains(value, ignoreCase = true) }
                    "patente" -> filteredClients.filter { it.patenteNumero.contains(value, ignoreCase = true) }
                    else -> filteredClients
                }
            }

            call.respond(filteredClients)
        }
    }
}
