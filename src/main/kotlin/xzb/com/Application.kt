package xzb.com

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import xzb.com.plugins.*

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0") {

        configureSecurity()
        configureMonitoring()
        configureTemplating()
        configureSerialization()
        configureSession()
        configureRouting()

        install(StatusPages) {
            exception<Throwable> { cause ->
                call.respond(HttpStatusCode.InternalServerError,cause.localizedMessage)
            }
        }

    }.start(wait = true)
}
