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
import xzb.com.service.DataService
import xzb.com.service.EurekaSvc
import xzb.com.service.JenkinsSvc
import xzb.com.service.SubwaySvc
import xzb.com.session.PortalSession
import java.io.File
import java.net.URLEncoder
import java.util.*

fun Application.configureRouting() {
    install(Locations) {
    }

    routing {
        static("assets") {
            resources("assets")
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
//                val jobs = jenkinsClient.getJobs()
               // jobs.forEach { (_, u) -> log.debug("duration =  ${u.details().lastBuild.details().duration}, time = ${Date(u.details().lastBuild.details().timestamp)}")}
                call.respond(VelocityContent("templates/index.vm", mutableMapOf("apps" to onlineServices.applications.application)))
            }
        }
        get("/jenkins")
        {
            val session = call.sessions.get<PortalSession>()
            if (session == null)
            {
                call.respondRedirect("login")
            }
            else
            {
                val jenkinsClient = JenkinsSvc().createJenkinsClient()
//                val jobs = jenkinsClient.getJobs()
                // jobs.forEach { (_, u) -> log.debug("duration =  ${u.details().lastBuild.details().duration}, time = ${Date(u.details().lastBuild.details().timestamp)}")}
                call.respond(VelocityContent("templates/jenkins.vm", mutableMapOf("jobs" to jenkinsClient.jobs)))
            }
        }

        get("subways")
        {
            val session = call.sessions.get<PortalSession>()
            if (session == null)
            {
                call.respondRedirect("login")
            }
            val subways = SubwaySvc().generateSubways()
            call.respond(VelocityContent("templates/subway.vm", mutableMapOf("subways" to subways)))
        }


        // file upload
        post("data/upload")
        {
            val multipart =  call.receiveMultipart()
            multipart.forEachPart {
                if (it is PartData.FileItem)
                {
                    DataService.upload(part = it)
                }
            }
            call.respond(HttpStatusCode.OK,"upload file ok")
        }

        get("/data/download") {

            val file = DataService.downLoad()
            if (file.exists())
            {
                val fileName = URLEncoder.encode("${file.name}","utf-8")
                call.response.header("Content-Disposition", "attachment; filename=\"$fileName\"")


                call.respondFile(file)
            }
            else call.respond(HttpStatusCode.NotFound)
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

