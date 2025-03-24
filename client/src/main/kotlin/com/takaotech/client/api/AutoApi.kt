package com.takaotech.client.api

import com.takaotech.client.model.AutoDTO
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

interface AutoApi {
    @GET("/auto")
    suspend fun getAllAuto(): List<AutoDTO>

    @GET("/auto/{id}")
    suspend fun getAutoById(@Path("id") id: Int): AutoDTO

    @GET("/auto/init")
    suspend fun initAuto(): AutoDTO
}