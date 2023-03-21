package com.crezent.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCors(){
    install(CORS){
        //Allowing all origins
       // allowHost("0.0.0.0:8000")
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.ContentType)
        anyHost()
        allowCredentials = true
    }
}