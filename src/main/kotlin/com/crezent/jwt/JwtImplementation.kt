package com.crezent.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class JwtImplementation : JwtInterface {
    override fun generateJwt( jwtClaim: JwtClaim, jwtConfig: JwtConfig): String {
        val jwtBuilder = JWT
            .create()
            .withIssuer(jwtConfig.issuer)
            .withAudience(jwtConfig.audience)
            .withExpiresAt(Date(System.currentTimeMillis() + jwtConfig.expireTime!!))
            .withClaim(jwtClaim.name,jwtClaim.value)

        return jwtBuilder.sign(Algorithm.HMAC256(jwtConfig.secretKey))
    }

    override fun verifyJwt( jwtConfig: JwtConfig, ): JWTVerifier {
       return  JWT
           .require(Algorithm.HMAC256(jwtConfig.secretKey))
           .withIssuer(jwtConfig.issuer)
           .withAudience(jwtConfig.audience)
           .build()
    }
}