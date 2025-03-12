package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import tables.Cliente

@Serializable
data class ClienteDTO(
    @SerialName("id") val id: Int,
    @SerialName("nome") val nome: String,
    @SerialName("cognome") val cognome: String,
    @SerialName("email") val email: String,
    @SerialName("telefono") val telefono: String,
    @SerialName("indirizzo") val indirizzo: String,
    @SerialName("patente_numero") val patenteNumero: String,
    @SerialName("data_scadenza_patente") val dataScadenzaPatente: String
) {
    companion object {
        fun fromEntity(entity: Cliente) = ClienteDTO(
            id = entity.id.value,
            nome = entity.nome,
            cognome = entity.cognome,
            email = entity.email,
            telefono = entity.telefono,
            indirizzo = entity.indirizzo,
            patenteNumero = entity.patenteNumero,
            dataScadenzaPatente = entity.dataScadenzaPatente.toString()
        )
    }
}

@Serializable
data class ClienteCreateDTO(
    @SerialName("nome") val nome: String,
    @SerialName("cognome") val cognome: String,
    @SerialName("email") val email: String,
    @SerialName("telefono") val telefono: String,
    @SerialName("indirizzo") val indirizzo: String,
    @SerialName("patente_numero") val patenteNumero: String,
    @SerialName("data_scadenza_patente") val dataScadenzaPatente: String
)
