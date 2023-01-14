package com.arenmarsden.backend.model

import org.jetbrains.exposed.sql.Table

data class Call(
    val id: Int,
    val name: String,
    val assignedTo: String,
    val status: Status,
    val type: Type,
    val description: String
)

object Calls : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val assignedTo = varchar("assignedTo", 50)
    val status = varchar("status", 50)
    val type = varchar("type", 50)
    val description = varchar("description", 50)

    override val primaryKey = PrimaryKey(id)
}

enum class Status {

    OPEN,
    IN_PROGRESS,
    CLOSED

}

enum class Type {

    ROBBERY,
    BURGLARY,
    THEFT,
    ASSAULT

}



