package com.android.rojox.core.repository

import com.android.rojox.core.domain.request.CommentRequest
import com.android.rojox.core.domain.request.PostRequest
import com.android.rojox.core.local.db.impl.DbClient
import com.android.rojox.core.model.DataState
import com.android.rojox.core.model.Comment
import com.android.rojox.core.model.Post
import com.android.rojox.core.remote.PostService
import com.android.rojox.core.utils.toEntity
import com.android.rojox.core.utils.toModel

class PostRepositoryImpl(
    private val apiClient: PostService,
    private val dbClient: DbClient
    ): PostRepository {

    override suspend fun getPosts(forceUpdate: Boolean): DataState<List<Post>> {

        var localPosts = if (!forceUpdate) {
            dbClient.getPost()
        } else emptyList()

        return try {
            if (localPosts.isEmpty()) {
                val remotePosts = apiClient.getPosts()
                val postEntities = remotePosts.map { it.toEntity() }
                dbClient.savePosts(postEntities)
                localPosts = postEntities
            }
            DataState.success(localPosts.map { it.toModel() })
        } catch (e: Exception)  {
            DataState.error(e)
        }
    }

    override suspend fun createPost(userId: Int, title: String, body: String): DataState<Post> {
        return try {
            DataState.success(apiClient.postPost(
                PostRequest(
                    userId, title, body
                )).toModel()
            )
        } catch (e: Exception) {
            DataState.error(e)
        }
    }

    override suspend fun getComments(postId: Int,forceUpdate: Boolean): DataState<List<Comment>> {

        var localComments = if (!forceUpdate) {
            dbClient.getComments(postId)
        } else emptyList()

        return try {
            if (localComments.isEmpty()) {
                val remoteComments = apiClient.getComments(postId)
                val commentEntities = remoteComments.map { it.toEntity() }
                dbClient.saveComments(commentEntities)
                localComments = commentEntities
            }
            DataState.success(localComments.map { it.toModel() })
        } catch (e: Exception)  {
            DataState.error(e)
        }
    }

    override suspend fun createComment(
        postId: Int,
        name: String,
        email: String,
        body: String
    ): DataState<Comment> {
        return try {
            DataState.success(apiClient.postComment(
                CommentRequest(
                    postId,name, email, body)
            ).toModel())
        } catch (e: Exception) {
            DataState.error(e)
        }
    }
}