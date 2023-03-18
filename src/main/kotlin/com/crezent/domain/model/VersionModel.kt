package com.crezent.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class VersionModel(
    val version: Double,
    val inherit: Boolean = true
)
