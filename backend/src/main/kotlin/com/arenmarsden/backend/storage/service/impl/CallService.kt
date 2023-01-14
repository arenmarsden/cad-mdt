package com.arenmarsden.backend.storage.service.impl

import com.arenmarsden.backend.model.Call
import com.arenmarsden.backend.model.Calls
import com.arenmarsden.backend.model.Status
import com.arenmarsden.backend.model.Type
import com.arenmarsden.backend.storage.DatabaseFactory.dbQuery
import com.arenmarsden.backend.storage.service.Service
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CallService : Service<Call> {
    override suspend fun getAll(): List<Call> = dbQuery {
        Calls.selectAll().map(::rowToResult)
    }

    override suspend fun get(id: Int): Call? = dbQuery {
        Calls.select { Calls.id eq id }.map(::rowToResult).singleOrNull()
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        Calls.deleteWhere { Calls.id eq id } > 0
    }

    override suspend fun deleteAll(): Boolean = dbQuery {
        Calls.deleteAll() > 0
    }

    override suspend fun update(t: Call): Call = dbQuery {
        Calls.update({ Calls.id eq t.id }) {
            it[name] = t.name
            it[assignedTo] = t.assignedTo
            it[status] = t.status.name
            it[type] = t.type.name
            it[description] = t.description
        }

        t
    }

    override suspend fun add(t: Call): Call = dbQuery {
        val id = Calls.insert {
            it[name] = t.name
            it[assignedTo] = t.assignedTo
            it[status] = t.status.name
            it[type] = t.type.name
            it[description] = t.description
        } get Calls.id

        t.copy(id = id)
    }

    override fun rowToResult(row: ResultRow): Call {
        return Call(
            id = row[Calls.id],
            name = row[Calls.name],
            assignedTo = row[Calls.assignedTo],
            status = Status.valueOf(row[Calls.status]),
            type = Type.valueOf(row[Calls.type]),
            description = row[Calls.description],
        )
    }

}