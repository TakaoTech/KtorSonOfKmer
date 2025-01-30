package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
)