package com.crezent.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class Ayahs(
    val data: List<AyahModel>
)
