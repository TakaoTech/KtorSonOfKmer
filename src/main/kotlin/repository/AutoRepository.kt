package repository

import model.AutoCreateDTO
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

    suspend fun add(auto: AutoCreateDTO) = database.dbExec {
        AutoEntity.new {
            marca = auto.marca
            modello = auto.modello
            targa = auto.targa
            annoImmatricolazione = auto.annoImmatricolazione
            chilometraggio = auto.chilometraggio
            costoGiornaliero = auto.costoGiornaliero
        }
    }
    suspend fun delete(id: Int) = database.dbExec { AutoEntity.findById(id)?.delete() }


    fun generateLicensePlate(): String {
        val letters = ('A'..'Z') - listOf('I', 'O', 'Q', 'U') // Escludiamo lettere ambigue
        val numbers = (0..9).map { it.toString() }

        val part1 = (1..2).map { letters.random() }.joinToString("")
        val part2 = (1..3).map { numbers.random() }.joinToString("")
        val part3 = (1..2).map { letters.random() }.joinToString("")

        return "$part1$part2$part3"
    }

}