package com.android.rojox.core.remote

import io.ktor.client.*

interface RemoteClient {
    val apiClient: HttpClient
}