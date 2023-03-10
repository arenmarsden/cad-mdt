package com.arenmarsden.backend.storage.service.impl

import com.arenmarsden.backend.model.Role
import com.arenmarsden.backend.model.User
import com.arenmarsden.backend.model.Users
import com.arenmarsden.backend.storage.DatabaseFactory.dbQuery
import com.arenmarsden.backend.storage.service.Service
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object UserService : Service<User> {
    override suspend fun getAll(): List<User> = dbQuery {
        Users.selectAll().map(::rowToResult)
    }

    override suspend fun get(id: Int): User? = dbQuery {
        Users.select { Users.id eq id }.map(::rowToResult).singleOrNull()
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }

    override suspend fun deleteAll(): Boolean = dbQuery {
        Users.deleteAll() > 0
    }

    override suspend fun update(t: User): User = dbQuery {
        Users.update({ Users.email eq t.email }) {
            it[name] = t.name
            it[email] = t.email
            it[role] = t.role.name
        }

        t
    }

    override suspend fun add(t: User): User? = dbQuery {
        val insertStatement = Users.insert {
            it[name] = t.name
            it[email] = t.email
            it[role] = t.role.name
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::rowToResult)
    }

    override fun rowToResult(row: ResultRow) = User(
        name = row[Users.name],
        email = row[Users.email],
        role = Role.valueOf(row[Users.role])
    )
}