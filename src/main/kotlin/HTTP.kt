package com.takaotech

import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import org.slf4j.event.Level

fun Application.configureHTTP() {
    install(CallLogging) {
        level = Level.DEBUG
    }
    routing {
        swaggerUI(path = "openapi")
    }
}
