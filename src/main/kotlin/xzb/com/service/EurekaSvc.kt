package xzb.com.service

import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import xzb.com.client.ClientFactory

class EurekaSvc {
    suspend fun getOnlineServices() :String{
        val client = ClientFactory.getClient();
        val responseData = client.get<String>(
            host = "192.168.138.167",
            port = 10010,
            path = "/eureka/apps"
        )
        {
            headers {

            }
        }
        return responseData
    }
}