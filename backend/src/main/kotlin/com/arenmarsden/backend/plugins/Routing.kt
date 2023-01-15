package com.arenmarsden.backend.plugins

import com.arenmarsden.backend.model.*
import com.arenmarsden.backend.storage.service.impl.CallService
import com.arenmarsden.backend.storage.service.impl.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking

fun Application.configureRouting() {
    routing {
        // Users

        UserService.apply {
            runBlocking {
                deleteAll()
                add(User(name = "Aren", email = "admin@example.com", role = Role.ADMIN))
            }
        }

        get("/users") {
            val users = UserService.getAll()
            call.respond(users)
        }
        post("/users") {
            val user = call.receive<User>()
            UserService.add(user)
            call.respond(HttpStatusCode.Created, user)
        }

        // Calls

        CallService.apply {
            runBlocking {
                deleteAll()
                add(
                    Call(
                        id = 1,
                        name = "Call 1",
                        assignedTo = UserService.getAll().first().name,
                        type = Type.BURGLARY,
                        status = Status.OPEN,
                        description = "Call 1 description"
                    )
                )
            }
        }

        get("/calls") {
            val calls = CallService.getAll()
            call.respond(calls)
        }
    }
}
