package com.android.rojox.core.utils

import com.android.rojox.core.domain.entity.CommentEntity
import com.android.rojox.core.domain.entity.PostEntity
import com.android.rojox.core.domain.response.CommentResponse
import com.android.rojox.core.domain.response.PostResponse
import com.android.rojox.core.model.Comment
import com.android.rojox.core.model.Post


fun CommentResponse.toModel() = Comment(
    postId,name, email, body
)

fun PostResponse.toModel() = Post(
    id, userId, title, body
)

fun PostResponse.toEntity() = PostEntity(
    id, userId, title, body
)

fun PostEntity.toModel() = Post(
    id, userId, title, body
)

fun CommentResponse.toEntity() = CommentEntity(
    id, postId,name, email, body
)

fun CommentEntity.toModel() = Comment(
    postId, name, email, body
)