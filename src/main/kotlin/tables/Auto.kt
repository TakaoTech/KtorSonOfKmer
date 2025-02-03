package tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Auto : IntIdTable("auto") {
    val marca = varchar("marca", 50)
    val modello = varchar("modello", 50)
    val targa = varchar("targa", 20).uniqueIndex()
    val annoImmatricolazione = integer("anno_immatricolazione")
    val chilometraggio = integer("chilometraggio")
    val stato = enumerationByName("stato", 20, StatoAuto::class).default(StatoAuto.DISPONIBILE)
    val costoGiornaliero = double("costo_giornaliero")
}

class AutoEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AutoEntity>(Auto)

    var marca by Auto.marca
    var modello by Auto.modello
    var targa by Auto.targa
    var annoImmatricolazione by Auto.annoImmatricolazione
    var chilometraggio by Auto.chilometraggio
    var stato by Auto.stato
    var costoGiornaliero by Auto.costoGiornaliero
    val noleggi by Noleggio referrersOn Noleggi.auto
}