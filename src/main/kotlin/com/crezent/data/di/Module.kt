package com.crezent.data.di

import com.crezent.data.FileHelper.JsonHelperImplementation
import com.crezent.data.FileHelper.JsonHelperInterface
import com.crezent.data.Json.RepoJsonImplementation
import com.crezent.data.database.RepoKmongoImplementation
import com.crezent.data.repository.QuranInterfaceRepo
import com.crezent.jwt.JwtImplementation
import com.crezent.jwt.JwtInterface
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val module =   module {

    single{
        val password = System.getenv("MONGO_PASSWORD")
        val databaseName = "QuranDatabase"
        KMongo
            .createClient(
            connectionString = "mongodb+srv://admin:$password@qurandatabase.pt5u5n0.mongodb.net/?retryWrites=true&w=majority"
            )
            .coroutine
            .getDatabase(databaseName)
    }

    single<JsonHelperInterface> {
        JsonHelperImplementation()
    }
    single <QuranInterfaceRepo>{
        RepoJsonImplementation(get())
    }
    single <JwtInterface> {
        JwtImplementation()
    }
}