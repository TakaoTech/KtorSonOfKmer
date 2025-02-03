package com.takaotech.routing

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.get
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import model.AutoCreateDTO
import org.koin.ktor.ext.get
import repository.AutoRepository
import kotlin.random.Random

fun Routing.autoRouting() {
    route("/auto") {
        get {
            val repository = get<AutoRepository>()
            call.respond(repository.getAll())
        }
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val repository = get<AutoRepository>()
            val auto = id?.let { repository.getById(it) }
            if (auto != null) call.respond(auto) else call.respond(HttpStatusCode.NotFound)
        }

        get("/init") {
            val repository = get<AutoRepository>()
            repository.add(
                AutoCreateDTO(
                    marca = listOf(
                        "Toyota",
                        "Alfa",
                        "Volkswagen"
                    ).random(),
                    modello = listOf(
                        "Yaris",
                        "Giulia",
                        "RS6"
                    ).random(),
                    targa = repository.generateLicensePlate(),
                    annoImmatricolazione = Clock.System.now().toLocalDateTime(TimeZone.UTC).year,
                    chilometraggio = Random.nextInt(10, 100000),
                    costoGiornaliero = Random.nextDouble(20.0, 50.0)
                )
            )
        }
    }
}

