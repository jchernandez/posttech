package com.android.rojox.core.local.db.impl

import com.android.rojox.core.domain.entity.CommentEntity
import com.android.rojox.core.domain.entity.PostEntity

interface DbClient {
    suspend fun getPost(): List<PostEntity>
    suspend fun getComments(postId: Int): List<CommentEntity>
    suspend fun savePosts(posts: List<PostEntity>)
    suspend fun saveComments(comments: List<CommentEntity>)
}