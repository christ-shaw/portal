val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.4.32"
}

group = "xzb.com"
version = "0.0.1"
application {
    mainClass.set("xzb.com.ApplicationKt")
}

repositories {
    mavenCentral()
    maven{url = uri("http://192.168.34.187:18081/repository/maven-public/")}
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
    maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-locations:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")
    implementation("io.ktor:ktor-html-builder:$ktor_version")
    implementation("org.jetbrains:kotlin-css-jvm:1.0.0-pre.129-kotlin-1.4.20")
    implementation("io.ktor:ktor-jackson:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation ("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-apache:$ktor_version")
    implementation("io.ktor:ktor-client-jackson:$ktor_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    implementation("io.ktor:ktor-velocity:$ktor_version")
    implementation("wu-cas:wu-cas-filter:0.1.10")
    implementation("io.ktor:ktor-server-sessions:$ktor_version")
    implementation("com.offbytwo.jenkins:jenkins-client:0.3.8")
    implementation ("com.github.doyaaaaaken:kotlin-csv-jvm:0.15.0")
    implementation("khttp:khttp:1.0.0")
    implementation("org.apache.poi:poi:4.1.2")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")

    implementation("org.jetbrains.exposed:exposed-core:0.31.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.31.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.31.1")
    implementation ("mysql:mysql-connector-java:8.0.19")
    implementation ("com.zaxxer:HikariCP:3.4.2")

}