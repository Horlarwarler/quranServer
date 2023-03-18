package com.crezent.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubResponse(
    val sha : String,
    @SerialName("download_url")
    val downloadUrl:String,
    val content:String

)
