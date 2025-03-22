package com.takaotech

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.event.Level

fun Application.configureHTTP() {
    install(CallLogging) {
        level = Level.DEBUG
    }

    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, _ ->
            call.respondText(
                text = "404: Errore",
                status = HttpStatusCode.NotFound
            )
        }
    }

    routing {
        swaggerUI(path = "openapi")
    }
}
