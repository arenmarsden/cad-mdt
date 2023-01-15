package com.arenmarsden.backend.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class User(val name: String, val email: String, val role: Role)

object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val email = varchar("email", 50)
    val role = varchar("role", 50)

    override val primaryKey = PrimaryKey(id)
}

enum class Role {

    ADMIN, OPERATOR, MEDIC, FIREMAN, POLICE, TOW, CITIZEN

}