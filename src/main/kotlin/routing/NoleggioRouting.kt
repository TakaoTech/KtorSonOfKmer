package com.takaotech.routing

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.get
import model.NoleggioCreateDTO
import org.koin.ktor.ext.get
import repository.NoleggioRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun Routing.noleggioRouting() {
    route("/noleggio") {
        get {
            val repository = get<NoleggioRepository>()
            call.respond(repository.getAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val repository = get<NoleggioRepository>()
            val noleggio = id?.let { repository.getById(it) }
            if (noleggio != null) call.respond(noleggio) else call.respond(HttpStatusCode.NotFound)
        }

        post {
            val repository = get<NoleggioRepository>()
            val noleggioCreateDTO = call.receive<NoleggioCreateDTO>()
            val noleggio = repository.add(noleggioCreateDTO)
            call.respond(HttpStatusCode.Created)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val repository = get<NoleggioRepository>()
                repository.delete(id)
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
            }
        }

        get("/init") {
            val repository = get<NoleggioRepository>()
            val today = LocalDate.now()
            val endDate = today.plusDays(7)
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE

            // Calculate total cost based on a daily rate of 50.0 and the number of days
            val days = ChronoUnit.DAYS.between(today, endDate)
            val totalCost = 50.0 * days

            repository.add(
                NoleggioCreateDTO(
                    clienteId = 1, // Assuming client with ID 1 exists
                    autoId = 1,    // Assuming auto with ID 1 exists
                    dataInizio = today.format(formatter),
                    dataFine = endDate.format(formatter),
                    costoTotale = totalCost,
                    stato = "ATTIVO"
                )
            )

            call.respond(HttpStatusCode.Created)
        }

        // Example using wildcard for filtering rentals by date range
        get("/filter/date/{startDate}/{endDate}") {
            val startDate = call.parameters["startDate"]
            val endDate = call.parameters["endDate"]
            val repository = get<NoleggioRepository>()
            val allRentals = repository.getAll()

            // Filter rentals that overlap with the given date range
            val filteredRentals = if (startDate != null && endDate != null) {
                val start = LocalDate.parse(startDate)
                val end = LocalDate.parse(endDate)

                allRentals.filter { noleggio ->
                    val rentalStart = LocalDate.parse(noleggio.dataInizio)
                    val rentalEnd = LocalDate.parse(noleggio.dataFine)

                    // Check if the rental overlaps with the given date range
                    (rentalStart.isBefore(end) || rentalStart.isEqual(end)) &&
                            (rentalEnd.isAfter(start) || rentalEnd.isEqual(start))
                }
            } else {
                allRentals
            }

            call.respond(filteredRentals)
        }
    }
}