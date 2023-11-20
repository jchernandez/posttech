package com.android.rojox.core.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.rojox.core.domain.response.PostResponse

@Entity
data class PostEntity(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)