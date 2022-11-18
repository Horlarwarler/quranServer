package com.crezent.routing

import com.crezent.data.repository.QuranInterfaceRepo
import com.crezent.domain.model.QuranResponseModel
import com.crezent.domain.model.SurahResponseModel
import com.crezent.domain.model.VersionModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.userRouting(
    quranInterfaceRepo: QuranInterfaceRepo
){
   get("/currentversion"){
       try {
           val currentVersion = quranInterfaceRepo.getCurrentVersion()
           val versionCode = VersionModel(versionCode = currentVersion).versionCode
           call.respond(
               HttpStatusCode.OK, VersionModel(versionCode = versionCode)
           )
       }
       catch (e:Exception){
           val errorMessage = e.message?:"Unknown Error "
           call.respond(HttpStatusCode.BadRequest, errorMessage)
       }

   }
    get("/updatedquran"){
        try {
            val quranModel =quranInterfaceRepo.getAllAyah()
            call.respond(HttpStatusCode.OK, QuranResponseModel(quran = quranModel))
        }
        catch (e:Exception){
            val errorMessage = e.message?:"Unknown Error "
            call.respond(HttpStatusCode.BadRequest, errorMessage)
        }

    }
    get("/allsurah"){
        try {
            val allSurah = quranInterfaceRepo.getAllSurah()
            call.respond(HttpStatusCode.OK, SurahResponseModel(surah = allSurah))
        }
        catch (e:Exception){
            val errorMessage = e.message?:"Unknown Error "
            call.respond(HttpStatusCode.BadRequest, errorMessage)
        }
    }
    get("ayah/{ayahId}"){
        try {
            val ayahId = call.parameters["ayahId"]?.toInt()?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, "Ayah Id is required")
                return@get
            }
            val ayah = quranInterfaceRepo.getAyah(ayahId)
            call.respond(HttpStatusCode.OK, ayah)
        }
        catch (e:Exception){
            val errorMessage = e.message?:"Unknown Error "
            call.respond(HttpStatusCode.BadRequest, errorMessage)
        }
    }
}