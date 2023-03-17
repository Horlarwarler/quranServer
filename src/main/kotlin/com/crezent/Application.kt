package com.crezent

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.crezent.plugins.*

fun main(args: Array<String>) {

    embeddedServer(
        Netty, port = 8080, host = "192.168.122.1", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureKoin()
    configureSecurity()
    configureRouting()
    configureSerialization()
}
