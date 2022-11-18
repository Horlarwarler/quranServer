package com.crezent.plugins

import com.crezent.data.repository.QuranInterfaceRepo
import com.crezent.jwt.JwtInterface
import com.crezent.routing.adminRouting
import com.crezent.routing.userRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

   val quranRepoInterface by inject<QuranInterfaceRepo>()
    val jwtInterface by inject<JwtInterface>()
    routing {
        adminRouting( quranRepoInterface, jwtInterface)
        userRouting(quranRepoInterface)
    }
}
