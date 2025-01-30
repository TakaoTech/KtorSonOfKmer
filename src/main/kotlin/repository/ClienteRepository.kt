package repository

import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.Factory
import tables.Cliente

@Factory
class ClienteRepository {
    fun getAll(): List<Cliente> = transaction { Cliente.all().toList() }
    fun getById(id: Int): Cliente? = transaction { Cliente.findById(id) }
    fun add(cliente: Cliente) = transaction { cliente }
    fun delete(id: Int) = transaction { Cliente.findById(id)?.delete() }
}
