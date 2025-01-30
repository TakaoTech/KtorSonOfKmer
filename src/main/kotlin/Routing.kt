package com.takaotech

import com.takaotech.routing.autoRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        autoRouting()
    }
}
