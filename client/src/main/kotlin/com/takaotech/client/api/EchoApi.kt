package com.takaotech.client.api

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import kotlinx.serialization.json.JsonElement

interface EchoApi {
    @POST("/echo")
    suspend fun echoText(@Body text: String): String

    @POST("/echoJson")
    suspend fun echoJson(@Body json: JsonElement): JsonElement

    @POST("/echoForms")
    suspend fun echoForms(@Body formData: Map<String, String>): String

    @GET("/echo404")
    suspend fun echo404()
}