package com.android.rojox.core.repository

import com.android.rojox.core.model.Comment
import com.android.rojox.core.model.DataState
import com.android.rojox.core.model.Post

interface PostRepository {

    suspend fun getPosts(loadCache: Boolean = false): DataState<List<Post>>

    suspend fun createPost(userId: Int, title: String, body: String): DataState<Post>

    suspend fun getComments(postId: Int, loadCache: Boolean = false): DataState<List<Comment>>

    suspend fun createComment(postId: Int, name: String, email: String, body: String): DataState<Comment>
}