package xzb.com

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import xzb.com.plugins.*

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0") {
        configureRouting()
        configureSecurity()
        configureMonitoring()
        configureTemplating()
        configureSerialization()
    }.start(wait = true)
}
