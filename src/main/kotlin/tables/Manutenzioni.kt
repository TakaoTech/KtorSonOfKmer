package tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

object Manutenzioni : IntIdTable("manutenzione") {
    val auto = reference("id_auto", Auto)
    val dataIntervento = date("data_intervento")
    val descrizione = text("descrizione")
    val costo = decimal("costo", 10, 2)
    val statoIntervento = enumerationByName("stato_intervento", 20, StatoIntervento::class)
}

class Manutenzione(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Manutenzione>(Manutenzioni)

    var auto by AutoEntity referencedOn Manutenzioni.auto
    var dataIntervento by Manutenzioni.dataIntervento
    var descrizione by Manutenzioni.descrizione
    var costo by Manutenzioni.costo
    var statoIntervento by Manutenzioni.statoIntervento
}