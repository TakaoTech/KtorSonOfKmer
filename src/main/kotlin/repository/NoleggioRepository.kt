package repository

import org.jetbrains.exposed.sql.transactions.transaction
import tables.Noleggio


class NoleggioRepository {
    fun getAll(): List<Noleggio> = transaction { Noleggio.all().toList() }
    fun getById(id: Int): Noleggio? = transaction { Noleggio.findById(id) }
    fun add(noleggio: Noleggio) = transaction { noleggio }
    fun delete(id: Int) = transaction { Noleggio.findById(id)?.delete() }
}