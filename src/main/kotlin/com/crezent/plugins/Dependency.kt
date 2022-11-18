package com.crezent.plugins

import com.crezent.data.di.module
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureKoin(){
    install(Koin){
        modules(modules = module)
    }
}