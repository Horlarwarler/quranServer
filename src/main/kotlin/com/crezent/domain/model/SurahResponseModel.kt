package com.crezent.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class SurahResponseModel(
    val surah: List<SurahModel>
)
