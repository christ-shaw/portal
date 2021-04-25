package xzb.com.client

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*

object ClientFactory {
    fun getClient() = HttpClient(Apache) {
        install(JsonFeature)
        engine {
            followRedirects = true
            socketTimeout = 10_000
            connectTimeout = 10_000
            connectionRequestTimeout = 20_000
        }
    }

}