package com.crezent.data.database

import com.crezent.data.mapper.convertToDatabase
import com.crezent.data.mapper.convertToModel
import com.crezent.data.models.QuranDatabaseModel
import com.crezent.data.models.VersionDatabaseModel
import com.crezent.data.repository.QuranInterfaceRepo
import com.crezent.domain.model.AyahModel
import com.crezent.domain.model.SurahModel
import com.crezent.domain.model.VersionModel
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.inc

class RepoKmongoImplementation(
    database: CoroutineDatabase
) : QuranInterfaceRepo {
    private val quranDatabase = database.getCollection<QuranDatabaseModel>()
    private val versionDatabase = database.getCollection<VersionDatabaseModel>()

    override suspend fun getVersion(): Double {
        return versionDatabase.find().first()?.versionCode ?: 1.0

    }

    override suspend fun getQuran(surahId: Int?, verseId: Int?): List<AyahModel> {
        return  when {
            surahId != null && verseId != null -> {
                quranDatabase
                    .find()
                    .filter(
                        AyahModel::surahId eq surahId
                    )
                    .filter(
                        AyahModel::verseId eq verseId
                    )
                    .toList()
                    .convertToModel()
            }
            surahId != null -> {
                quranDatabase
                    .find()
                    .filter(
                        AyahModel::surahId eq surahId
                    )
                    .toList()
                    .convertToModel()
            }
            else -> {
                quranDatabase.find().toList().convertToModel()
            }
        }
    }

    override suspend fun updateQuran(updateQuran: List<AyahModel>) {
        updateQuran.forEach {
            quranModel ->
           quranDatabase.updateOne(
            filter = QuranDatabaseModel::_id eq quranModel._id,
               quranModel.convertToDatabase()
           )
        }
       updateVersionCode()
    }

    private suspend fun updateVersionCode(){
        val currentVersion = versionDatabase.find().first()?: kotlin.run {
            val newVersion = VersionModel(
                version = 1.0
            )
            versionDatabase.insertOne(newVersion.convertToDatabase())
            return

        }

        versionDatabase.updateOne(
           filter =  VersionDatabaseModel::versionCode eq currentVersion.versionCode,
            update = inc(VersionDatabaseModel::versionCode , 0.1)
        )


    }

    override suspend fun getSurah(): List<SurahModel> {
        TODO("Not yet implemented")
    }

//    override suspend fun getAyah(uniqueId: Int): AyahModel {
//        TODO("Not yet implemented")
//    }
}