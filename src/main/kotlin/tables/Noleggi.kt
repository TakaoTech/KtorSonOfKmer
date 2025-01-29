package tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

object Noleggi : IntIdTable("noleggi") {
    val cliente = reference("id_cliente", Clienti)
    val auto = reference("id_auto", Auto)
    val dataInizio = date("data_inizio")
    val dataFine = date("data_fine")
    val costoTotale = decimal("costo_totale", 10, 2)
    val statoNoleggio = enumerationByName("stato_noleggio", 20, StatoNoleggio::class)
}

class Noleggio(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Noleggio>(Noleggi)

    var cliente by Cliente referencedOn Noleggi.cliente
    var auto by AutoEntity referencedOn Noleggi.auto
    var dataInizio by Noleggi.dataInizio
    var dataFine by Noleggi.dataFine
    var costoTotale by Noleggi.costoTotale
}