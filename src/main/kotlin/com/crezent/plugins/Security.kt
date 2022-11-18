package com.crezent.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.crezent.jwt.JwtConfig
import com.crezent.jwt.JwtInterface
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {

    val jwtInterface by inject<JwtInterface> ()
    val domain = "https://jwt-provider-domain/"
    val audience = "jwt-audience"
    val issuer = "jwt-issuer"
    val realmValue = "ktor sample app"
    val secretKey = System.getenv("SECRET_KEY")!!
    val envUsername = System.getenv("USER_NAME")!!
    val config = JwtConfig(
        audience = audience,
        secretKey = secretKey,
        issuer = issuer,
    )
    install(Authentication){
        jwt{
           realm = realmValue
            verifier(jwtInterface.verifyJwt(config))
            validate {
                jwtCredential ->
                if(jwtCredential.payload.claims.containsKey("username"))JWTPrincipal(jwtCredential.payload) else null
            }
        }
    }

}
