package com.arenmarsden.backend.plugins

import com.arenmarsden.backend.model.Call
import com.arenmarsden.backend.model.User
import com.arenmarsden.backend.storage.service.impl.CallService
import com.arenmarsden.backend.storage.service.impl.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    configureUsersRoute()
    configureCallsRoute()
}

private fun Application.configureUsersRoute() {
    routing {
        get("/users") {
            val users = UserService.getAll()
            call.respond(users)
        }

        post("/users") {
            val user = call.receive<User>()
            UserService.add(user)
            call.respond(HttpStatusCode.Created, user)
        }
    }
}

private fun Application.configureCallsRoute() {
    routing {
        get("/calls") {
            val calls = CallService.getAll()
            call.respond(calls)
        }

        get("/call/:id") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val receivedCall = CallService.get(id.toInt())
            receivedCall?.let { call.respond(it) } ?: call.respond(HttpStatusCode.NotFound)
        }

        post("/calls") {
            val receivedCall = call.receive<Call>()
            CallService.add(receivedCall)
            call.respond(HttpStatusCode.Created, receivedCall)
        }
    }
}
