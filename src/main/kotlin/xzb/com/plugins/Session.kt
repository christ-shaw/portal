package xzb.com.plugins

import io.ktor.application.*
import io.ktor.sessions.*
import xzb.com.session.PortalSession

fun Application.configureSession() {
    install(Sessions) {
        cookie<PortalSession>("SESSION") {
        }
    }
}