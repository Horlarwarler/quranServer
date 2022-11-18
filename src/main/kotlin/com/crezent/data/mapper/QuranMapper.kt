package com.crezent.data.mapper

import com.crezent.data.models.QuranDatabaseModel
import com.crezent.domain.model.AyahModel

fun AyahModel.convertToDatabase(): QuranDatabaseModel{
    return  QuranDatabaseModel(
        surahId, verseId, arabic, translation, footnote,_id
    )
}

fun QuranDatabaseModel.convertToModel():AyahModel{
    return  AyahModel(
        surahId, ayahId, arabic, translation, footnote, _id
    )
}

fun List<AyahModel>.convertToDatabase(): List<QuranDatabaseModel>{
    return  this.map {
        it.convertToDatabase()
    }
}

fun List<QuranDatabaseModel>.convertToModel():List<AyahModel>{
    return  this.map {
        it.convertToModel()
    }
}