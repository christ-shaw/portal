package xzb.com.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.sessions.*
import io.ktor.velocity.*
import xzb.com.service.EurekaSvc
import xzb.com.session.PortalSession

fun Application.configureRouting() {
    install(Locations) {
    }

    routing {
        static("assets") {
            resources("css")
        }
        authenticate("form") {
            post("/login") {
                call.sessions.set(PortalSession( call.principal<UserIdPrincipal>()!!.name))
                call.respondRedirect("/")
            }
        }
        get("/login/failed")
        {
            call.respondText("login failed",
                contentType = ContentType.parse("application/text"),status = HttpStatusCode.OK)
        }

        get("login")
        {
            call.respond(VelocityContent("templates/login.vm", mapOf("user" to "user")))
        }


        get("/")
        {
            val session = call.sessions.get<PortalSession>()
            if (session == null)
            {
              call.respondRedirect("login")
            }
            else
            {
                val onlineServices = EurekaSvc().getOnlineServices();
                call.respond(onlineServices)
            }
        }


//        get<MyLocation> {
//            call.respondText("Location: name=${it.name}, arg1=${it.arg1}, arg2=${it.arg2}")
//        }
//        // Register nested routes
//        get<Type.Edit> {
//            call.respondText("Inside $it")
//        }
//        get<Type.List> {
//            call.respondText("Inside $it")
//        }
//    }
    }
}

//@Location("/location/{name}")
//class MyLocation(val name: String, val arg1: Int = 42, val arg2: String = "default")
//@Location("/type/{name}")
//data class Type(val name: String) {
//    @Location("/edit")
//    data class Edit(val type: Type)
//
//    @Location("/list/{page}")
//    data class List(val type: Type, val page: Int)

