package com.crezent.data.di

import com.crezent.data.FileHelper.JsonHelperImplementation
import com.crezent.data.FileHelper.JsonHelperInterface
import com.crezent.data.Json.GithubJsonFileImplementation
import com.crezent.data.repository.QuranInterfaceRepo
import com.crezent.jwt.JwtImplementation
import com.crezent.jwt.JwtInterface
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*

import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val module = module {

    single {
        val password = System.getenv("MONGO_PASSWORD")
        val databaseName = "QuranDatabase"
        KMongo
            .createClient(
                connectionString = "mongodb+srv://admin:$password@qurandatabase.pt5u5n0.mongodb.net/?retryWrites=true&w=majority"
            )
            .coroutine
            .getDatabase(databaseName)
    }

    single {
        HttpClient(CIO) {
            install(Auth) {
                bearer {
                    loadTokens {
                        // Load tokens from a local storage and return them as the 'BearerTokens' instance
                        BearerTokens("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","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")
                    }
                }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    },
                    contentType = ContentType.Any

                )
            }
        }
    }

    single<JsonHelperInterface> {
        JsonHelperImplementation()
    }
    single<QuranInterfaceRepo> {
        GithubJsonFileImplementation(get())
        //RepoJsonImplementation(get())
    }
    single<JwtInterface> {
        JwtImplementation()
    }
}