package tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

object Clienti : IntIdTable("clienti") {
    val nome = varchar("nome", 100)
    val cognome = varchar("cognome", 100)
    val email = varchar("email", 100).uniqueIndex()
    val telefono = varchar("telefono", 20)
    val indirizzo = text("indirizzo")
    val patenteNumero = varchar("patente_numero", 50).uniqueIndex()
    val dataScadenzaPatente = date("data_scadenza_patente")
}

class Cliente(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Cliente>(Clienti)

    var nome by Clienti.nome
    var cognome by Clienti.cognome
    var email by Clienti.email
    var telefono by Clienti.telefono
    var indirizzo by Clienti.indirizzo
    var patenteNumero by Clienti.patenteNumero
    var dataScadenzaPatente by Clienti.dataScadenzaPatente
    val noleggi by Noleggio referrersOn Noleggi.cliente
}
