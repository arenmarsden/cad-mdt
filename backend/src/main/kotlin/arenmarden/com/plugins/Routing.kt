package arenmarden.com.plugins

import arenmarden.com.storage.repository.impl.userRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get {
            call.respondText(mapOf("users" to userRepository.getAllUsers()).toString())
        }
    }
}
