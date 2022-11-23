package com.crezent.data.Json

import com.crezent.Util.roundUp
import com.crezent.Util.updateQuran
import com.crezent.data.FileHelper.JsonHelperInterface
import com.crezent.data.repository.QuranInterfaceRepo
import com.crezent.domain.model.AyahModel
import com.crezent.domain.model.SurahModel
import com.crezent.domain.model.VersionModel

class RepoJsonImplementation(
    private val jsonHelperInterface: JsonHelperInterface
) : QuranInterfaceRepo {
    private val versionJsonFileName = "files/version.json"
    private val quranSerializer = AyahModel.serializer()
    private val quranJsonFileName = "files/quran.json"
    private val versionSerializer = VersionModel.serializer()
    private val surahFileName = "files/surah.json"
    private val surahSerializer = SurahModel.serializer()

    override suspend fun getVersion(): Double {

        val versionJson = jsonHelperInterface.readFromJsonFile(
            versionJsonFileName,
            versionSerializer
        ).first()
        return  versionJson.versionCode
    }

    override suspend fun getQuran(surahId: Int?, verseId: Int?): List<AyahModel> {
        val quran = jsonHelperInterface.readFromJsonFile(
            quranJsonFileName,
            quranSerializer
        )
        return when{
            surahId != null && verseId != null ->{
                quran.filter {
                    it.surahId == surahId && it.verseId == verseId
                }
            }
            surahId != null ->{
                quran.filter {
                    it.surahId == surahId
                }
            }
            else -> {
                quran
            }
        }


    }

    override suspend fun updateQuran(updateQuran: List<AyahModel>) {

        val currentQuranJson = getQuran()
        val latestQuran = currentQuranJson.updateQuran(updateQuran)
        jsonHelperInterface.writeToJsonFile(
            quranJsonFileName,
            quranSerializer,
            latestQuran
        )
        updateVersion()
    }

    private suspend fun updateVersion(){
        val currentVersion = getVersion()
        // 1.0 + 0.1
        val latestVersion = (currentVersion + 0.1).roundUp()
        val versionModel = VersionModel(
            versionCode = latestVersion
        )
        val versions = listOf(versionModel)
        jsonHelperInterface.writeToJsonFile(
            versionJsonFileName,
            versionSerializer,
            versions
        )

    }

    override suspend fun getSurah(): List<SurahModel> {
        return jsonHelperInterface.readFromJsonFile(
            fileName = surahFileName,
            contentSerializer = surahSerializer
        )
    }

//    override suspend fun getAyah(uniqueId: Int): AyahModel {
//        val allAyah = getAllAyah()
//        return allAyah.findAyah(uniqueId)
//    }
}