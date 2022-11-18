package com.crezent.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SurahModel(
    val ayahNumber:Int,
    val id:Int,
    val surahArabic:String,
    val surahTranslation:String,
    val type:String
)
