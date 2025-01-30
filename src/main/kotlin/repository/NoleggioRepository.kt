package repository

import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.Factory
import tables.Noleggio

@Factory
class NoleggioRepository {
    fun getAll(): List<Noleggio> = transaction { Noleggio.all().toList() }
    fun getById(id: Int): Noleggio? = transaction { Noleggio.findById(id) }
    fun add(noleggio: Noleggio) = transaction { noleggio }
    fun delete(id: Int) = transaction { Noleggio.findById(id)?.delete() }
}