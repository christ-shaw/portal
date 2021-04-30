package xzb.com.service

import com.offbytwo.jenkins.JenkinsServer
import java.net.URI

class JenkinsSvc {
    fun createJenkinsClient(): JenkinsServer =
        JenkinsServer(URI("http://192.168.132.2:8000"),"MG01863","MG01863")
}