package xzb.com.plugins

import com.wu.cas.filter.Assertion
import com.wu.cas.filter.ResponseData
import com.wu.cas.filter.http.HttpUtils
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.request.*
import io.ktor.response.*
import io.ktor.routing.*
import xzb.com.client.ClientFactory

fun Application.configureSecurity() {

    authentication {
        basic(name = "myauth1") {
            realm = "Ktor Server"
            validate { credentials ->
                if (credentials.name == credentials.password) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }

        form(name = "form") {
            userParamName = "username"
            passwordParamName = "password"
            challenge ("login/failed")
            validate { credentials ->
                val validated = loginForAPP(credentials.name,credentials.password)
                return@validate if (validated.isOK)
                {
                    val data = validated.data as Map<String,String>
                     UserIdPrincipal(data["userName"]!!)
                }
                else
                {
                    null
                }
            }
        }
    }

    routing {
        authenticate("myauth1") {
            get("/protected/route/basic") {
                val principal = call.principal<UserIdPrincipal>()!!
                call.respondText("Hello ${principal.name}")
            }
        }
        authenticate("myauth1") {
            get("/protected/route/form") {
                val principal = call.principal<UserIdPrincipal>()!!
                call.respondText("Hello ${principal.name}")
            }
        }
    }
}


suspend fun loginForAPP(user:String, password:String): ResponseData
{
    val params = mapOf("userName" to user,"password" to password,"loginType" to "ACCOUNT")
    val httpParams = HttpUtils.generateUrl(params)
    val client = ClientFactory.getClient()
    val response = client.get<ResponseData>(
        host = "auth.worldunion.com.cn",
        path = "wu-cas-web/forms/auth/loginForApp?$httpParams",


        ) {

    }
   return response

}