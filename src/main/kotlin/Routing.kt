package com.takaotech

import com.takaotech.routing.autoRouting
import com.takaotech.routing.clienteRouting
import com.takaotech.routing.echoRouting
import com.takaotech.routing.noleggioRouting
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        autoRouting()
        clienteRouting()
        echoRouting()

        authenticate("noleggio-auth") {
            noleggioRouting()
        }
    }
}
