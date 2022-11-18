package com.crezent.data.repository

import com.crezent.domain.model.AyahModel
import com.crezent.domain.model.SurahModel

interface QuranInterfaceRepo {
    suspend fun getCurrentVersion() : Double
    suspend fun getAllAyah(): List<AyahModel>
    suspend fun updateQuran(updateQuran: List<AyahModel>)
    suspend fun getAllSurah(): List<SurahModel>
    suspend fun getAyah(uniqueId:Int): AyahModel
}