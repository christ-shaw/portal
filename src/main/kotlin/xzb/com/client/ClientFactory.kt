package xzb.com.client

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import xzb.com.model.LeaseInfo
import xzb.com.model.TimestampDeserialize
import java.text.SimpleDateFormat
import java.util.*

object ClientFactory {
    fun getClient() = HttpClient(Apache) {
        install(Logging)
        install(JsonFeature)
        {
            serializer = JacksonSerializer(){
                enable(SerializationFeature.INDENT_OUTPUT)
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:SS" , Locale.CHINA)
                val module = SimpleModule()
                module.addDeserializer(LeaseInfo::class.java, TimestampDeserialize())
                this.registerModule(module)
            }
        }
        engine {
            followRedirects = true
            socketTimeout = 10_000
            connectTimeout = 10_000
            connectionRequestTimeout = 20_000
        }
    }

}