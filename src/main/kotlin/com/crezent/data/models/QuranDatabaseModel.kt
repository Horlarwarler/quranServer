package com.crezent.data.models

data class QuranDatabaseModel(
    val surahId:Int,
    val ayahId:Int,
    val arabic:String,
    val translation:String,
    val footnote: String?,
    val _id: Int
)