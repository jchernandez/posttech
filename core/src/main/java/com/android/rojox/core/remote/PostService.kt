package com.android.rojox.core.remote

import com.android.rojox.core.domain.request.CommentRequest
import com.android.rojox.core.domain.request.PostRequest
import com.android.rojox.core.domain.response.CommentResponse
import com.android.rojox.core.domain.response.PostResponse

interface PostService {
    suspend fun getPosts(): List<PostResponse>
    suspend fun getComments(postId: Int): List<CommentResponse>
    suspend fun postPost(request: PostRequest): PostResponse
    suspend fun postComment(request: CommentRequest): CommentResponse
}