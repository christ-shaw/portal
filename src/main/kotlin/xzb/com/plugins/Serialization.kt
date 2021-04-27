package xzb.com.plugins

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import xzb.com.model.LeaseInfo
import xzb.com.model.TimestampDeserialize
import java.text.SimpleDateFormat
import java.util.*


fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:SS" , Locale.CHINA)
            val module = SimpleModule()
            module.addDeserializer(LeaseInfo::class.java, TimestampDeserialize())
            this.registerModule(module)
        }
    }

    routing {
        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}
