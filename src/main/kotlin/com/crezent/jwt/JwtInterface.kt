package com.crezent.jwt

import com.auth0.jwt.JWTVerifier

interface JwtInterface {
    fun generateJwt(jwtClaim: JwtClaim, jwtConfig: JwtConfig):String

    fun verifyJwt(jwtConfig: JwtConfig):JWTVerifier
}