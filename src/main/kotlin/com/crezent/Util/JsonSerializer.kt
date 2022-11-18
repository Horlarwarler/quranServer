package com.crezent.Util

import kotlinx.serialization.Serializable


@Serializable
data class JsonSerializer<T>(
    val data: List<T>
)
