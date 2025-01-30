package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import tables.AutoEntity

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
) {
    companion object {
        fun fromEntity(entity: AutoEntity) = AutoDTO(
            id = entity.id.value,
            marca = entity.marca,
            modello = entity.modello,
            targa = entity.targa,
            annoImmatricolazione = entity.annoImmatricolazione,
            chilometraggio = entity.chilometraggio,
            stato = entity.stato.name,
            costoGiornaliero = entity.costoGiornaliero.toDouble()
        )
    }
}
