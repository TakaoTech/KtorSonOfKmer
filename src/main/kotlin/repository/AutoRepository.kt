package repository

import org.jetbrains.exposed.sql.transactions.transaction
import tables.AutoEntity


class AutoRepository {
    fun getAll(): List<AutoEntity> = transaction { AutoEntity.all().toList() }
    fun getById(id: Int): AutoEntity? = transaction { AutoEntity.findById(id) }
    fun add(auto: AutoEntity) = transaction { auto }
    fun delete(id: Int) = transaction { AutoEntity.findById(id)?.delete() }
}