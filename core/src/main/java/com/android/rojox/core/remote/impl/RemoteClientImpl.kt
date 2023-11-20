package com.android.rojox.core.remote.impl


import com.android.rojox.core.remote.RemoteClient
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class RemoteClientImpl: RemoteClient {
    override val apiClient: HttpClient
        get() = HttpClient(Android) {
            expectSuccess = true
            install(HttpTimeout) {
                requestTimeoutMillis = 60000
                socketTimeoutMillis = 60000
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }

        }.also {
            Napier.base(DebugAntilog())
        }
}