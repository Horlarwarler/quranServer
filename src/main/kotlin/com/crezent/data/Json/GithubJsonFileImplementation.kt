package com.crezent.data.Json

import com.crezent.Util.updateQuran
import com.crezent.data.repository.QuranInterfaceRepo
import com.crezent.domain.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Base64

class GithubJsonFileImplementation(
    private val client: HttpClient
) : QuranInterfaceRepo {

    override suspend fun getVersion(): Double {
        val versionJson = client.get("https://raw.githubusercontent.com/Horlarwarler/quranServer/master/files/version.json")
        val response = versionJson.body<VersionModel>()
        return response.version
    }

    override suspend fun getQuran(surahId: Int?, verseId: Int?): List<AyahModel> {
        val quranJson = client.get("https://raw.githubusercontent.com/Horlarwarler/quranServer/master/files/quran.json")
        val quran = quranJson.body<Ayahs>().data
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
        try {
            val url = "https://api.github.com/repos/Horlarwarler/quranServer/contents/files/quran.json?ref=master"
            val quranGithub = client.get(url)
           val quranGithubModel = quranGithub.body<GithubResponse>()
            val decodedQuran = getQuran()
            val newUpdatedQuran = decodedQuran.updateQuran(updateQuran)
            val updatedQuran = Ayahs(
                newUpdatedQuran
            )
            val encodedToString = Json.encodeToString(updatedQuran)
            val encodedToBase64 = Base64.getEncoder().encodeToString(encodedToString.toByteArray())
            val updateBody = GithubUpdateModel(
                message = "Update Quran ",
                content = encodedToBase64,
                sha = quranGithubModel.sha
            )
            updateContentGithub(url, updateBody)

            println("Updated version Suceess" )
          //  updateVersion()
        }

        catch (error:Exception){
            println("Updated version ${error.message}" )
        }


    }

    private suspend fun updateVersion(){
        val url = "https://api.github.com/repos/Horlarwarler/quranServer/contents/files/version.json?ref=master"

        val versionGithub = client.get(url)
        val versionGithubModel = versionGithub.body<GithubResponse>()
        val versionCode = getVersion()
        val versionModel = VersionModel(version = versionCode + 0.1)
        val encodedToString = Json.encodeToString(versionModel)
        val encodedToBase64 = Base64.getEncoder().encodeToString(encodedToString.toByteArray())
        val updateBody = GithubUpdateModel(
            message = "Updated Version ",
            content = encodedToBase64,
            sha = versionGithubModel.sha
        )
        updateContentGithub(url, updateBody)

        println("Updated version $encodedToBase64" )

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
        val surahJson = client.get("https://raw.githubusercontent.com/Horlarwarler/quranServer/master/files/surah.json")
       return surahJson.body<Surahs>().data

    }
}