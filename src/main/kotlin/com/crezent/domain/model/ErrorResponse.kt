package com.crezent.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val errorMessage : String
)
