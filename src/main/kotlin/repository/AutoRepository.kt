package repository

import model.AutoDTO
import org.koin.core.annotation.Factory
import tables.AutoEntity
import tables.HikariDatabase

@Factory
class AutoRepository(
    val database: HikariDatabase
) {
    suspend fun getAll(): List<AutoDTO> = database.dbExec { AutoEntity.all().toList() }
        .map {
            AutoDTO.fromEntity(it)
        }

    suspend fun getById(id: Int): AutoDTO? = database.dbExec { AutoEntity.findById(id) }?.let {
        AutoDTO.fromEntity(it)
    }

    suspend fun add(auto: AutoEntity) = database.dbExec { auto }
    suspend fun delete(id: Int) = database.dbExec { AutoEntity.findById(id)?.delete() }
}