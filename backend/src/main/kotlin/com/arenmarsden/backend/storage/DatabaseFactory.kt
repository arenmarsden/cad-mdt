package com.arenmarsden.backend.storage

import com.arenmarsden.backend.model.Calls
import com.arenmarsden.backend.model.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        val driver = "org.postgresql.Driver"
        val jdbcUrl = System.getenv("JDBC_DATABASE_URL")
        val username = System.getenv("JDBC_DATABASE_USERNAME")
        val password = System.getenv("JDBC_DATABASE_PASSWORD")
        val database = Database.connect(jdbcUrl, driver, username, password)

        transaction(database) {
            SchemaUtils.create(Users, Calls, inBatch = true)
        }
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

}