package com.crezent.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val username :String,
    val password:String
)
