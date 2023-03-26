package com.crezent.data.repository

import com.crezent.domain.model.AyahModel
import com.crezent.domain.model.SurahModel

interface QuranInterfaceRepo {
    suspend fun getVersion() : Double
    suspend fun getQuran(surahId:Int? = null, verseId:Int? = null): List<AyahModel>
    suspend fun updateQuran(ayahModel: AyahModel)
    suspend fun getSurah(): List<SurahModel>

    suspend fun updateVersion()
    //suspend fun getAyah(uniqueId:Int): AyahModel
}