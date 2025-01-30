package com.takaotech.routing

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.get
import org.koin.ktor.ext.get
import repository.AutoRepository

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
    }
}