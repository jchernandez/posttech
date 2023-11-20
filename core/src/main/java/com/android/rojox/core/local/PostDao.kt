package com.android.rojox.core.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.rojox.core.domain.entity.CommentEntity
import com.android.rojox.core.domain.entity.PostEntity

@Dao
interface PostDao {


    @Query("SELECT * FROM PostEntity")
    suspend fun getPosts(): List<PostEntity>

    @Query("SELECT * FROM CommentEntity WHERE postId = :id")
    suspend fun getComments(id: Int): List<CommentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPosts(items: List<PostEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllComments(items: List<CommentEntity>)
}