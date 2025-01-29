package tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

object Pagamenti : IntIdTable("pagamenti") {
    val noleggio = reference("id_noleggio", Noleggi)
    val dataPagamento = date("data_pagamento")
    val importo = decimal("importo", 10, 2)
    val metodoPagamento = enumerationByName("metodo_pagamento", 20, MetodoPagamento::class)
    val statoPagamento = enumerationByName("stato_pagamento", 20, StatoPagamento::class)
}

class Pagamento(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Pagamento>(Pagamenti)

    var noleggio by Noleggio referencedOn Pagamenti.noleggio
    var dataPagamento by Pagamenti.dataPagamento
    var importo by Pagamenti.importo
    var metodoPagamento by Pagamenti.metodoPagamento
    var statoPagamento by Pagamenti.statoPagamento
}