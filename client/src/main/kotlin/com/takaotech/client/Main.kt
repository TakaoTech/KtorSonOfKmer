package com.takaotech.client

import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.util.*

suspend fun main() {
    val client = KtorFitClient("http://localhost:8080/")

    try {
        // Test Auto API
        println("=== Testing Auto API ===")
        val autos = client.autoApi.getAllAuto()
        println("Found ${autos.size} autos")

        if (autos.isNotEmpty()) {
            val auto = client.autoApi.getAutoById(autos.first().id)
            println("Auto details: ${auto.marca} ${auto.modello}, Targa: ${auto.targa}")
        }

        // Test Cliente API
        println("\n=== Testing Cliente API ===")
        val clienti = client.clienteApi.getAllClienti()
        println("Found ${clienti.size} clienti")

        if (clienti.isNotEmpty()) {
            val cliente = client.clienteApi.getClienteById(clienti.first().id)
            println("Cliente details: ${cliente.nome} ${cliente.cognome}, Email: ${cliente.email}")
        }

        // Test Echo API
        println("\n=== Testing Echo API ===")
        val echoResponse = client.echoApi.echoText("Hello, Ktorfit!")
        println("Echo response: $echoResponse")

        val jsonObject = buildJsonObject {
            put("name", "Ktorfit Client")
            put("version", "1.0.0")
        }
        val jsonResponse = client.echoApi.echoJson(jsonObject)
        println("JSON Echo response: $jsonResponse")

        // Test Noleggio API (requires authentication)
        println("\n=== Testing Noleggio API ===")
        try {
            // Basic authentication with username:password
            val auth = "Basic " + Base64.getEncoder().encodeToString("admin:admin".toByteArray())
            val noleggi = client.noleggioApi.getAllNoleggi(auth)
            println("Found ${noleggi.size} noleggi")

            if (noleggi.isNotEmpty()) {
                val noleggio = client.noleggioApi.getNoleggioById(auth, noleggi.first().id)
                println("Noleggio details: Cliente ID: ${noleggio.clienteId}, Auto ID: ${noleggio.autoId}")
            }
        } catch (e: Exception) {
            println("Error accessing Noleggio API: ${e.message}")
        }

    } catch (e: Exception) {
        println("Error: ${e.message}")
        e.printStackTrace()
    } finally {
        client.close()
    }
}