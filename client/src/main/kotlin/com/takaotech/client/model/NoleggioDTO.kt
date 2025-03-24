package com.takaotech.client.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoleggioDTO(
    @SerialName("id") val id: Int,
    @SerialName("cliente_id") val clienteId: Int,
    @SerialName("auto_id") val autoId: Int,
    @SerialName("data_inizio") val dataInizio: String,
    @SerialName("data_fine") val dataFine: String,
    @SerialName("costo_totale") val costoTotale: Double,
    @SerialName("stato") val stato: String
)

@Serializable
data class NoleggioCreateDTO(
    @SerialName("cliente_id") val clienteId: Int,
    @SerialName("auto_id") val autoId: Int,
    @SerialName("data_inizio") val dataInizio: String,
    @SerialName("data_fine") val dataFine: String,
    @SerialName("costo_totale") val costoTotale: Double,
    @SerialName("stato") val stato: String
)