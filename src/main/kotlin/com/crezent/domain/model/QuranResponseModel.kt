package com.crezent.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class QuranResponseModel<T>(
    val quran : T
)
