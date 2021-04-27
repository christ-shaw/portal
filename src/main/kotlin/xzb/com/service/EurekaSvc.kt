package xzb.com.service

import io.ktor.client.request.*
import io.ktor.http.*
import xzb.com.client.ClientFactory
import xzb.com.model.OnlineService

class EurekaSvc {
    suspend fun getOnlineServices(): OnlineService {
        val client = ClientFactory.getClient();
        return client.get(
            host = "192.168.138.167",
            port = 10010,
            path = "/eureka/apps"
        )
        {
            headers {
               contentType(ContentType.parse("application/json"))
            }
        }
    }
}