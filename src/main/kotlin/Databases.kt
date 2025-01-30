package com.takaotech

import io.ktor.server.application.*
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.get
import tables.HikariDatabase

fun Application.configureDatabases() {
    val database = get<HikariDatabase>()
    runBlocking {
        database.connect()
    }
}
