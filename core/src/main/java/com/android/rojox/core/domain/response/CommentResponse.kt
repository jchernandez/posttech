package com.android.rojox.core.domain.response

@kotlinx.serialization.Serializable
data class CommentResponse(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)