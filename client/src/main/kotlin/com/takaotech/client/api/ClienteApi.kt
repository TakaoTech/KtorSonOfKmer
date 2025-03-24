package com.takaotech.client.api

import com.takaotech.client.model.ClienteCreateDTO
import com.takaotech.client.model.ClienteDTO
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path

interface ClienteApi {
    @GET("/cliente")
    suspend fun getAllClienti(): List<ClienteDTO>

    @POST("/cliente")
    suspend fun createCliente(@Body cliente: ClienteCreateDTO)

    @GET("/cliente/{id}")
    suspend fun getClienteById(@Path("id") id: Int): ClienteDTO

    @DELETE("/cliente/{id}")
    suspend fun deleteCliente(@Path("id") id: Int)

    @GET("/cliente/init")
    suspend fun initCliente(): ClienteDTO

    @GET("/cliente/search/{term}/*")
    suspend fun searchClienti(@Path("term") term: String): List<ClienteDTO>

    @GET("/cliente/filter/{field}/{value}/**")
    suspend fun filterClienti(
        @Path("field") field: String,
        @Path("value") value: String
    ): List<ClienteDTO>

    @GET("/cliente/patente/{patente}")
    suspend fun getClientiByPatente(@Path("patente") patente: String): List<ClienteDTO>
}