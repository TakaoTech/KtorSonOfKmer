package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import tables.Noleggio

@Serializable
data class NoleggioDTO(
    @SerialName("id") val id: Int,
    @SerialName("cliente_id") val clienteId: Int,
    @SerialName("auto_id") val autoId: Int,
    @SerialName("data_inizio") val dataInizio: String,
    @SerialName("data_fine") val dataFine: String,
    @SerialName("costo_totale") val costoTotale: Double,
    @SerialName("stato") val stato: String
) {
    companion object {
        fun fromEntity(entity: Noleggio) = NoleggioDTO(
            id = entity.id.value,
            clienteId = entity.cliente.id.value,
            autoId = entity.auto.id.value,
            dataInizio = entity.dataInizio.toString(),
            dataFine = entity.dataFine.toString(),
            costoTotale = entity.costoTotale.toDouble(),
            stato = "ATTIVO" // Default value since statoNoleggio is not accessible
        )
    }
}

@Serializable
data class NoleggioCreateDTO(
    @SerialName("cliente_id") val clienteId: Int,
    @SerialName("auto_id") val autoId: Int,
    @SerialName("data_inizio") val dataInizio: String,
    @SerialName("data_fine") val dataFine: String,
    @SerialName("costo_totale") val costoTotale: Double,
    @SerialName("stato") val stato: String
)
