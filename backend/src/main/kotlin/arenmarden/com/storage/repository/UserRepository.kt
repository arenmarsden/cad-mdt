package arenmarden.com.storage.repository

import arenmarden.com.model.User

interface UserRepository {

    suspend fun getAllUsers(): List<User>
    suspend fun getUserById(id: Int): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun addUser(user: User): User
    suspend fun updateUser(user: User): User
    suspend fun deleteUser(id: Int): Boolean

}