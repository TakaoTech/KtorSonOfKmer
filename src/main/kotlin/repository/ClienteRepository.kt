package repository

import kotlinx.datetime.toKotlinLocalDate
import model.ClienteCreateDTO
import model.ClienteDTO
import org.koin.core.annotation.Factory
import tables.Cliente
import tables.HikariDatabase
import java.time.LocalDate

@Factory
class ClienteRepository(
    val database: HikariDatabase
) {
    suspend fun getAll(): List<ClienteDTO> = database.dbExec { Cliente.all().toList() }
        .map {
            ClienteDTO.fromEntity(it)
        }

    suspend fun getById(id: Int): ClienteDTO? = database.dbExec { Cliente.findById(id) }?.let {
        ClienteDTO.fromEntity(it)
    }

    suspend fun add(cliente: ClienteCreateDTO) = database.dbExec {
        Cliente.new {
            nome = cliente.nome
            cognome = cliente.cognome
            email = cliente.email
            telefono = cliente.telefono
            indirizzo = cliente.indirizzo
            patenteNumero = cliente.patenteNumero
            dataScadenzaPatente = LocalDate.parse(cliente.dataScadenzaPatente).toKotlinLocalDate()
        }
    }

    suspend fun delete(id: Int) = database.dbExec { Cliente.findById(id)?.delete() }
}
