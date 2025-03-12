package repository

import kotlinx.datetime.toKotlinLocalDate
import model.NoleggioCreateDTO
import model.NoleggioDTO
import org.koin.core.annotation.Factory
import tables.AutoEntity
import tables.Cliente
import tables.HikariDatabase
import tables.Noleggio
import java.math.BigDecimal
import java.time.LocalDate

@Factory
class NoleggioRepository(
    val database: HikariDatabase
) {
    suspend fun getAll(): List<NoleggioDTO> = database.dbExec { Noleggio.all().toList() }
        .map {
            NoleggioDTO.fromEntity(it)
        }

    suspend fun getById(id: Int): NoleggioDTO? = database.dbExec { Noleggio.findById(id) }?.let {
        NoleggioDTO.fromEntity(it)
    }

    suspend fun add(noleggio: NoleggioCreateDTO) = database.dbExec {
        val cliente = Cliente.findById(noleggio.clienteId) ?: throw IllegalArgumentException("Cliente not found")
        val auto = AutoEntity.findById(noleggio.autoId) ?: throw IllegalArgumentException("Auto not found")

        Noleggio.new {
            this.cliente = cliente
            this.auto = auto
            dataInizio = LocalDate.parse(noleggio.dataInizio).toKotlinLocalDate()
            dataFine = LocalDate.parse(noleggio.dataFine).toKotlinLocalDate()
            costoTotale = BigDecimal(noleggio.costoTotale.toString())
        }
    }

    suspend fun delete(id: Int) = database.dbExec { Noleggio.findById(id)?.delete() }
}