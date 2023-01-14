package com.arenmarsden.backend.storage.service

import org.jetbrains.exposed.sql.ResultRow

interface Service<T> {

    suspend fun getAll(): List<T>
    suspend fun get(id: Int): T?
    suspend fun add(t: T): T
    suspend fun update(t: T): T
    suspend fun delete(id: Int): Boolean
    suspend fun deleteAll(): Boolean

    fun rowToResult(row: ResultRow): T {
        TODO("Not yet implemented")
    }

}