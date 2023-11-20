package com.android.rojox.core.remote

object HttpRoutes {

    private const val BASE_API = "https://jsonplaceholder.typicode.com"
    const val POSTS = "$BASE_API/posts"
    const val COMMENTS = "$BASE_API/comments"

    fun buildCommentsRoute(postId: Int) = "$POSTS/$postId/comments"

}