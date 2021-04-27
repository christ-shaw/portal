package xzb.com.model

import java.time.LocalDateTime
import java.util.*

data class OnlineService(val applications: Applications)

data class Applications(val application: List<Application>,val versions__delta:String)

data class Application(val name:String ,val instance:List<Instance>)


data class Instance(val homePageUrl:String,val leaseInfo: LeaseInfo)

data class LeaseInfo(val registrationTime: Date,
                     val serviceUpTime :Date,
                     val lastRenewalTime:Date?
 )

