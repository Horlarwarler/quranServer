package com.crezent.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class AyahModel(
    val surahId:Int,
    val verseId:Int,
    val arabic:String,
    val translation:String,
    val footnote: String?,
    val _id:Int
)
