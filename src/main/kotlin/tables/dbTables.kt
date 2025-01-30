package tables

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SchemaUtils.withDataBaseLock
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.Singleton

@Singleton
class HikariDatabase(
) {
    lateinit var database: Database

    fun connect() {
        database = Database.connect(
            url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            user = "root",
            driver = "org.h2.Driver",
            password = "",
        )
        setupSchema()
    }

    fun disconnect() {

    }

    private fun setupSchema() {
        transaction(database) {
            withDataBaseLock {
                SchemaUtils.createMissingTablesAndColumns(*dbTables)
            }
        }
    }

    suspend fun <T> dbExec(statement: suspend Transaction.() -> T): T =
        withContext(Dispatchers.IO) {
            newSuspendedTransaction(statement = statement)
        }
}

val dbTables =
    arrayOf(
        Auto,
        Clienti,
        Manutenzioni,
        Noleggi,
        Pagamenti
    )