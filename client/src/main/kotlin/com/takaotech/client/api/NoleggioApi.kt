package com.takaotech.client.api

import com.takaotech.client.model.NoleggioCreateDTO
import com.takaotech.client.model.NoleggioDTO
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path

interface NoleggioApi {
    @GET("/noleggio")
    suspend fun getAllNoleggi(@Header("Authorization") authorization: String): List<NoleggioDTO>

    @POST("/noleggio")
    suspend fun createNoleggio(
        @Header("Authorization") authorization: String,
        @Body noleggio: NoleggioCreateDTO
    )

    @GET("/noleggio/{id}")
    suspend fun getNoleggioById(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ): NoleggioDTO

    @DELETE("/noleggio/{id}")
    suspend fun deleteNoleggio(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    )

    @GET("/noleggio/init")
    suspend fun initNoleggio(@Header("Authorization") authorization: String): NoleggioDTO

    @GET("/noleggio/filter/date/{startDate}/{endDate}")
    suspend fun filterNoleggiByDate(
        @Header("Authorization") authorization: String,
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String
    ): List<NoleggioDTO>
}