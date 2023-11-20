package com.android.rojox.core.utils

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json


private val json = Json {
    ignoreUnknownKeys = true
}

internal inline fun <reified R : Any> String.convertToDataClass() =
    json.decodeFromString<R>(this)


@Throws(Exception::class)
suspend fun HttpClient.post(
    urlString: String,
    body: Any,
    block: HttpRequestBuilder.() -> Unit = {}
): HttpResponse = post {
    url(urlString)
    contentType(ContentType.Application.Json)
    setBody(body)
    block()
}