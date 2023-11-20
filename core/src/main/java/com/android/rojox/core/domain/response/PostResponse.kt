package com.android.rojox.core.domain.response

import kotlinx.serialization.*

@Serializable
data class PostResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
