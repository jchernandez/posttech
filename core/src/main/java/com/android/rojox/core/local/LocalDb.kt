package com.android.rojox.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.rojox.core.domain.entity.CommentEntity
import com.android.rojox.core.domain.entity.PostEntity

@Database(entities = [CommentEntity::class, PostEntity::class], version = 1)
abstract class LocalDb: RoomDatabase() {
    abstract val postDao: PostDao
}