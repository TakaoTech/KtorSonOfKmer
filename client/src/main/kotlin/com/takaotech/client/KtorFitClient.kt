package com.takaotech.client

import com.takaotech.client.api.AutoApi
import com.takaotech.client.api.ClienteApi
import com.takaotech.client.api.EchoApi
import com.takaotech.client.api.NoleggioApi
import com.takaotech.client.api.createAutoApi
import com.takaotech.client.api.createClienteApi
import com.takaotech.client.api.createEchoApi
import com.takaotech.client.api.createNoleggioApi
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class KtorFitClient(baseUrl: String) {
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    private val ktorfit = Ktorfit.Builder()
        .httpClient(httpClient)
        .baseUrl(baseUrl)
        .build()

    val autoApi: AutoApi = ktorfit.createAutoApi()
    val clienteApi: ClienteApi = ktorfit.createClienteApi()
    val noleggioApi: NoleggioApi = ktorfit.createNoleggioApi()
    val echoApi: EchoApi = ktorfit.createEchoApi()

    fun close() {
        httpClient.close()
    }
}