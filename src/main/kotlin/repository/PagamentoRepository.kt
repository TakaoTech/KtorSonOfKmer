package repository

import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.Factory
import tables.Pagamento

@Factory
class PagamentoRepository {
    fun getAll(): List<Pagamento> = transaction { Pagamento.all().toList() }
    fun getById(id: Int): Pagamento? = transaction { Pagamento.findById(id) }
    fun add(pagamento: Pagamento) = transaction { pagamento }
    fun delete(id: Int) = transaction { Pagamento.findById(id)?.delete() }
}