package com.arenmarsden.backend

import com.arenmarsden.backend.storage.DatabaseFactory
import com.arenmarsden.backend.plugins.configureHTTP
import com.arenmarsden.backend.plugins.configureRouting
import com.arenmarsden.backend.plugins.configureSecurity
import com.arenmarsden.backend.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    DatabaseFactory.init()
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureRouting()
}
