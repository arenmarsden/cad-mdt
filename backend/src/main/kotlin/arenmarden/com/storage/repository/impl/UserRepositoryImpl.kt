package arenmarden.com.storage.repository.impl

import arenmarden.com.model.Role
import arenmarden.com.model.User
import arenmarden.com.model.Users
import arenmarden.com.storage.DatabaseFactory.dbQuery
import arenmarden.com.storage.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class UserRepositoryImpl : UserRepository {

    override suspend fun getAllUsers(): List<User> = dbQuery {
        Users.selectAll().map(::rowToUser)
    }

    override suspend fun getUserById(id: Int): User? = dbQuery {
        Users.select { Users.id eq id }.map(::rowToUser).singleOrNull()
    }

    override suspend fun getUserByEmail(email: String): User? = dbQuery {
        Users.select { Users.email eq email }.map(::rowToUser).singleOrNull()
    }

    override suspend fun addUser(user: User): User = dbQuery {
        val id = Users.insert {
            it[name] = user.name
            it[email] = user.email
            it[role] = user.role.name
        } get Users.id

        user.copy(id = id)
    }

    override suspend fun updateUser(user: User): User = dbQuery {
        Users.update({ Users.id eq user.id }) {
            it[name] = user.name
            it[email] = user.email
            it[role] = user.role.name
        }

        user
    }

    override suspend fun deleteUser(id: Int): Boolean = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }

    private fun rowToUser(row: ResultRow): User {
        return User(
            id = row[Users.id],
            name = row[Users.name],
            email = row[Users.email],
            role = Role.valueOf(row[Users.role])
        )
    }
}

val userRepository = UserRepositoryImpl().apply {
    runBlocking {
        if (getAllUsers().isEmpty()) {
            addUser(User(0, "Admin", "admin@example.com", Role.ADMIN))
        }
    }
}