package com.takaotech.client.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AutoDTO(
    @SerialName("id") val id: Int,
    @SerialName("marca") val marca: String,
    @SerialName("modello") val modello: String,
    @SerialName("targa") val targa: String,
    @SerialName("anno_immatricolazione") val annoImmatricolazione: Int,
    @SerialName("chilometraggio") val chilometraggio: Int,
    @SerialName("stato") val stato: String,
    @SerialName("costo_giornaliero") val costoGiornaliero: Double
)

@Serializable
data class AutoCreateDTO(
    @SerialName("marca") val marca: String,
    @SerialName("modello") val modello: String,
    @SerialName("targa") val targa: String,
    @SerialName("anno_immatricolazione") val annoImmatricolazione: Int,
    @SerialName("chilometraggio") val chilometraggio: Int,
    @SerialName("costo_giornaliero") val costoGiornaliero: Double
)