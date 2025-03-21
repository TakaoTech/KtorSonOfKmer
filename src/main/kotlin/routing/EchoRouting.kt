package com.takaotech.routing

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import nl.adaptivity.xmlutil.serialization.XmlElement

@Serializable
data class EchoResponse(@XmlElement val body: Map<String, JsonObject>)

@Serializable
data class EchoResponseXml(@SerialName("body") @XmlElement val body: SimpleXmlObject)

@Serializable
data class SimpleXmlObject(@XmlElement val id: Int, @XmlElement val name: String)

fun Routing.echoRouting() {
    post("/echo") {
        call.respondText(call.receiveText())
    }

    post("/echoJson") {
        val contentType = call.request.contentType()
        val accept = call.request.accept()

        when (contentType) {
            ContentType.Application.Json -> {
                // Receive the input as JsonObject
                val jsonObject = call.receive<JsonObject>()

                // Check if the Accept header is XML
                if (accept?.startsWith(ContentType.Application.Xml.toString()) == true) {
                    // Extract SimpleXmlObject from the JSON if it exists
                    val simpleXmlObject = jsonObject["SimpleXmlObject"]?.let {
                        if (it is JsonObject) {
                            val id = it["id"]?.toString()?.trim('"')?.toIntOrNull() ?: 0
                            val name = it["name"]?.toString()?.trim('"') ?: ""
                            SimpleXmlObject(id, name)
                        } else null
                    }

                    // If SimpleXmlObject was extracted, respond with EchoResponseXml
                    if (simpleXmlObject != null) {
                        call.respond(EchoResponseXml(simpleXmlObject))
                    } else {
                        // Fallback to JSON response if SimpleXmlObject couldn't be extracted
                        call.respond(EchoResponse(mapOf("body" to jsonObject)))
                    }
                } else {
                    // Create the response object for JSON
                    val response = EchoResponse(mapOf("body" to jsonObject))
                    call.respond(response)
                }
            }

            ContentType.Application.Xml -> {
                val xmlObject = runCatching {
                    call.receive<SimpleXmlObject>()
                }

                call.respond(EchoResponseXml(xmlObject.getOrThrow()))
            }
        }
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
