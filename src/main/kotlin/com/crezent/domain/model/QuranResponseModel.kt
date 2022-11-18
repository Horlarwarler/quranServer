package com.crezent.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class QuranResponseModel(
    val quran : List<AyahModel>
)
