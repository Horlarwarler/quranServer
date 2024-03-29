val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project
val kmongo_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.21"
    id("io.ktor.plugin") version "2.1.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"



}

group = "com.crezent"
version = "0.0.1"
application {
    mainClass.set("com.crezent.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-client-content-negotiation:2.1.3")
    implementation("io.ktor:ktor-serialization-gson:2.1.3")
    implementation("io.ktor:ktor-client-auth:2.1.3")
    implementation("io.ktor:ktor-server-cors-jvm:2.1.3")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")


    // koin
    implementation ("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    runtimeOnly("io.insert-koin:koin-core:$koin_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")



    //Kmongo
    implementation ("org.litote.kmongo:kmongo-coroutine:$kmongo_version")
    implementation ("org.litote.kmongo:kmongo:$kmongo_version")

    //
    implementation("io.ktor:ktor-server-cors:$ktor_version")







}