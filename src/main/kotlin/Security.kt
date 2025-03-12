package com.takaotech

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureSecurity() {
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
    }

    authentication {
        basic("noleggio-auth") {
            realm = "Noleggio Routes"
            validate { credentials ->
                if (credentials.name == "admin" && credentials.password == "admin") {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
}