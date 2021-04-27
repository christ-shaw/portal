package xzb.com.model

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.io.IOException
import java.time.Instant
import java.time.ZoneId
import java.util.*


class TimestampDeserialize @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<LeaseInfo>(vc) {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext?): LeaseInfo {
        val node: JsonNode = jp.codec.readTree(jp)
        val registration =  Date(node.get("registrationTimestamp").asLong())
        val lastRenewal = Date(node.get("lastRenewalTimestamp").asLong())
        val serviceUpTime = Date(node.get("serviceUpTimestamp").asLong())

        return LeaseInfo(
            registrationTime = registration,
            lastRenewalTime =  lastRenewal,
            serviceUpTime = serviceUpTime
        )

    }
}