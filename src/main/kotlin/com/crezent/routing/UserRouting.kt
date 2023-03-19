package com.crezent.routing

import com.crezent.data.repository.QuranInterfaceRepo
import com.crezent.domain.model.QuranResponseModel
import com.crezent.domain.model.Surahs
import com.crezent.domain.model.VersionModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Routing.userRouting(
    quranInterfaceRepo: QuranInterfaceRepo
){
   get("/version"){
       try {
           val currentVersion = quranInterfaceRepo.getVersion()
           val version = VersionModel(version = currentVersion).version
           call.respond(
               HttpStatusCode.OK, VersionModel(version = version)
           )
       }
       catch (e:Exception){
           val errorMessage = e.message?:"Unknown Error "
           call.respond(HttpStatusCode.BadRequest, errorMessage)
       }

   }
    get("/quran"){
        try {
            val surah = call.request.queryParameters["surahId"]?.toInt()
            val ayah = call.request.queryParameters["ayahId"]?.toInt()
            val quran =quranInterfaceRepo.getQuran(surah, ayah)
            if(quran.size == 1){
                call.respond(HttpStatusCode.OK, QuranResponseModel(quran = quran[0]))
                return@get
            }
            call.respond(HttpStatusCode.OK, QuranResponseModel(quran = quran))
        }
        catch (e:Exception){
            val errorMessage = e.message?:"Unknown Error "
            call.respond(HttpStatusCode.BadRequest, errorMessage)
        }

    }
    get("/surah"){
        try {
            val allSurah = quranInterfaceRepo.getSurah()
            call.respond(HttpStatusCode.OK, Surahs(data = allSurah))
        }
        catch (e:Exception){
            val errorMessage = e.message?:"Unknown Error "
            call.respond(HttpStatusCode.BadRequest, errorMessage)
        }
    }

}