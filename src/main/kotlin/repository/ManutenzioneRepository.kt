package repository

import org.jetbrains.exposed.sql.transactions.transaction
import tables.Manutenzione

class ManutenzioneRepository {
    fun getAll(): List<Manutenzione> = transaction { Manutenzione.all().toList() }
    fun getById(id: Int): Manutenzione? = transaction { Manutenzione.findById(id) }
    fun add(manutenzione: Manutenzione) = transaction { manutenzione }
    fun delete(id: Int) = transaction { Manutenzione.findById(id)?.delete() }
}