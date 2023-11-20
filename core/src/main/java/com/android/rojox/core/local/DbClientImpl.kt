package com.android.rojox.core.local

import com.android.rojox.core.domain.entity.CommentEntity
import com.android.rojox.core.domain.entity.PostEntity
import com.android.rojox.core.local.db.impl.DbClient

class DbClientImpl(private val postDao: PostDao): DbClient {
    override suspend fun getPost(): List<PostEntity> = postDao.getPosts()
    override suspend fun getComments(postId: Int): List<CommentEntity> = postDao.getComments(postId)
    override suspend fun savePosts(posts: List<PostEntity>) = postDao.insertAllPosts(posts)
    override suspend fun saveComments(comments: List<CommentEntity>) = postDao.insertAllComments(comments)
}