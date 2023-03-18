package com.crezent.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class Surahs(
    val data: List<SurahModel>
)
