package com.takaotech.routing

import io.ktor.http.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import model.AutoCreateDTO
import model.AutoResource
import org.koin.ktor.ext.inject
import repository.AutoRepository
import kotlin.random.Random

fun Routing.autoRouting() {
    // Get all autos
    get<AutoResource.All> {
        val repository by inject<AutoRepository>()
        call.respond(repository.getAll())
    }

    // Get auto by ID
    get<AutoResource.ById> { resource ->
        val repository by inject<AutoRepository>()
        val auto = repository.getById(resource.id)
        if (auto != null) call.respond(auto) else call.respond(HttpStatusCode.NotFound)
    }

    // Initialize with a random auto
    get<AutoResource.Init> {
        val repository by inject<AutoRepository>()
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
        call.respond(HttpStatusCode.Created)
    }
}
