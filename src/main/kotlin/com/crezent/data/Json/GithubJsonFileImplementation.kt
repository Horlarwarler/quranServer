package com.crezent.data.Json

import com.crezent.Util.roundUp
import com.crezent.Util.updateQuran
import com.crezent.data.repository.QuranInterfaceRepo
import com.crezent.domain.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

import io.ktor.util.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.math.BigDecimal
import java.time.LocalDate

import kotlin.math.round

class GithubJsonFileImplementation(
    private val client: HttpClient
) : QuranInterfaceRepo {

    var cachedSurahModel : List<SurahModel>? = null
    var cachedAyahModel: List<AyahModel>? = null
    var cachedVersion:Double? = null

    override suspend fun getVersion(): Double {

        if (cachedVersion!= null){
           return  cachedVersion!!
        }
        val url = "https://raw.githubusercontent.com/Horlarwarler/quranServer/master/files/version.json"

        val versionJson = client.get(url)
        val response = versionJson.body<VersionModel>()
        val roundVersion = response.version.roundUp(1)
        cachedVersion = roundVersion
        return cachedVersion!!
    }

    override suspend fun getQuran(surahId: Int?, verseId: Int?): List<AyahModel> {
        val url = "https://raw.githubusercontent.com/Horlarwarler/quranServer/master/files/quran.json"
        lateinit var quran:List<AyahModel>
        if (cachedAyahModel != null){
            quran =   cachedAyahModel!!
        }
        else{
            val quranJson = client.get(url)
            quran = quranJson.body<Ayahs>().data
            cachedAyahModel = quran
        }
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

    override suspend fun updateQuran(ayahModel: AyahModel) {
        val url = "https://api.github.com/repos/Horlarwarler/quranServer/contents/files/quran.json"
        val quranGithub = client.get(url)
        val quranGithubModel = quranGithub.body<GithubResponse>()
        val quran = getQuran()
        val  newUpdatedQuran = quran.updateQuran(ayahModel)
        val updatedQuran = Ayahs(
            newUpdatedQuran
        )
        val encodedToString = Json.encodeToString(updatedQuran)
        val encodedToBase64 =  encodedToString.encodeBase64()
        val updateBody = GithubUpdateModel(
            message = "Update Quran ",
            content = encodedToBase64,
            sha = quranGithubModel.sha
        )
        updateContentGithub(url, updateBody)
        cachedAyahModel = newUpdatedQuran
    }



    override suspend fun updateVersion(){

        val url = "https://api.github.com/repos/Horlarwarler/quranServer/contents/files/version.json"

        val versionGithub = client.get(url)
        val versionGithubModel = versionGithub.body<GithubResponse>()
        val content = versionGithubModel.content

        val decodedString = content.decodeBase64String()
        val version = Json.decodeFromString<VersionModel>(decodedString )
        val roundVersion = version.version.roundUp(1)
        val bigVersion = BigDecimal("$roundVersion")

        val newVersion = bigVersion.add(BigDecimal("0.1")).toDouble()
        val versionModel = VersionModel(version = newVersion)
        val encodedToString = Json.encodeToString(versionModel)
        val encodedToBase64 = encodedToString.encodeBase64()
        val updateBody = GithubUpdateModel(
            message = "Updated Version ",
            content = encodedToBase64,
            sha = versionGithubModel.sha
        )
        updateContentGithub(url, updateBody)
        cachedVersion = versionModel.version

    }

    private suspend fun updateContentGithub(url:String, updateBody:GithubUpdateModel){
        client.put(url){
            contentType(ContentType.Application.Json)
            setBody(
                updateBody
            )
        }

    }

    override suspend fun getSurah(): List<SurahModel> {
        if (cachedSurahModel != null){
            return  cachedSurahModel!!
        }
        val surahJson = client.get("https://raw.githubusercontent.com/Horlarwarler/quranServer/master/files/surah.json")
        cachedSurahModel = surahJson.body<Surahs>().data
        return cachedSurahModel!!

    }
}