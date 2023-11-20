package com.android.rojox.core.domain.request

@kotlinx.serialization.Serializable
data class CommentRequest(
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)