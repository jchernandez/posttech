package com.android.rojox.core.domain.request

@kotlinx.serialization.Serializable
data class PostRequest(
    val userId: Int,
    val title: String,
    val body: String
)