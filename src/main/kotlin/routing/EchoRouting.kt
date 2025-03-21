package com.takaotech.routing

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import kotlinx.serialization.json.JsonObject

fun Routing.echoRouting() {
    post("/echo") {
        call.respondText(call.receiveText())
    }

    post("/echoJson") {
        call.respond(
            mapOf("body" to call.receive<JsonObject>())
        )
    }

    post("/echoForms") {
        val formParameters = call.receiveParameters()
        val username = formParameters["username"]
        call.respondText("The '$username' account is created")
    }

    post("/echoUpload") {
        val multipart = call.receiveMultipart()
        var fileName = ""
        var fileBytes: ByteArray? = null

        multipart.forEachPart { part ->
            when (part) {
                is PartData.FileItem -> {
                    fileName = part.originalFileName ?: "uploaded-file"
                    fileBytes = part.provider().toByteArray()
                }

                else -> {}
            }
            part.dispose()
        }

        if (fileBytes != null) {
            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, fileName).toString()
            )
            call.respondBytes(fileBytes!!, ContentType.defaultForFilePath(fileName))
        } else {
            call.respond(HttpStatusCode.BadRequest, "No file uploaded")
        }
    }
}
