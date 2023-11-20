package com.android.rojox.posttechhold.test.utils

import com.android.rojox.core.model.Comment
import com.android.rojox.core.model.Post

object TestUtils {

    val DEFAULT_POST = Post(
        TestConstants.USER_ID,
        TestConstants.POST_ID,
        TestConstants.POST_TITLE,
        TestConstants.POST_BODY
    )
    val DEFAULT_COMMENT = Comment(
        TestConstants.POST_ID,
        TestConstants.NAME,
        TestConstants.EMAIL,
        TestConstants.COMMENT_BODY
    )

}