package com.takaotech

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureSecurity() {
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
    }
}
