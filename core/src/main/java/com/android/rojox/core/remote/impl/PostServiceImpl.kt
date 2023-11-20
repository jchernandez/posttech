package com.android.rojox.core.remote.impl

import com.android.rojox.core.domain.request.CommentRequest
import com.android.rojox.core.domain.request.PostRequest
import com.android.rojox.core.domain.response.CommentResponse
import com.android.rojox.core.domain.response.PostResponse
import com.android.rojox.core.remote.HttpRoutes
import com.android.rojox.core.remote.PostService
import com.android.rojox.core.remote.RemoteClient
import com.android.rojox.core.utils.post
import io.ktor.client.call.*
import io.ktor.client.request.*

class PostServiceImpl(private val remoteClient: RemoteClient): PostService {
    override suspend fun getPosts(): List<PostResponse> {
        return remoteClient.apiClient.get(HttpRoutes.POSTS).body()
    }

    override suspend fun getComments(postId: Int): List<CommentResponse> {
        return remoteClient.apiClient.get(HttpRoutes.buildCommentsRoute(postId)).body()
    }

    override suspend fun postPost(request: PostRequest): PostResponse {
        return remoteClient.apiClient.post(HttpRoutes.POSTS, request).body()
    }

    override suspend fun postComment(request: CommentRequest): CommentResponse {
        return remoteClient.apiClient.post(HttpRoutes.COMMENTS, request).body()
    }
}