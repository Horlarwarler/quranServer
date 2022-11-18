package com.crezent.jwt

data class JwtConfig(
    val issuer: String,
    val audience:String,
    val expireTime: Long? = null,
    val secretKey: String

)
