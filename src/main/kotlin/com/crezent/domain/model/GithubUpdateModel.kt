package com.crezent.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class GithubUpdateModel(
    val message:String,
    val content:String,
    val sha:String
)
