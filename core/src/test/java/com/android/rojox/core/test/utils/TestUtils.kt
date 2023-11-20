package com.android.rojox.core.test.utils

import com.android.rojox.core.domain.entity.CommentEntity
import com.android.rojox.core.domain.entity.PostEntity
import com.android.rojox.core.domain.response.CommentResponse
import com.android.rojox.core.domain.response.PostResponse
import com.android.rojox.core.model.Post

object TestUtils {

    val DEFAULT_POSTS_RESPONSE = PostResponse(
        TestConstants.USER_ID,
        TestConstants.POST_ID,
        TestConstants.POST_TITLE,
        TestConstants.POST_BODY
    )

    val DEFAULT_POST_ENTITY = PostEntity(
        TestConstants.POST_ID,
        TestConstants.USER_ID,
        TestConstants.POST_TITLE,
        TestConstants.POST_BODY
    )

    val DEFAULT_COMMENT_ENTITY = CommentEntity(
        TestConstants.COMMENT_ID,
        TestConstants.POST_ID,
        TestConstants.NAME,
        TestConstants.EMAIL,
        TestConstants.COMMENT_BODY
    )

    val DEFAULT_COMMENT_RESPONSE = CommentResponse(
        TestConstants.COMMENT_ID,
        TestConstants.POST_ID,
        TestConstants.NAME,
        TestConstants.EMAIL,
        TestConstants.COMMENT_BODY
    )

}