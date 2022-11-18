package com.crezent.Util

import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse<T>(
    val data: T? = null
)
