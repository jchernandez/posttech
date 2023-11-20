package com.android.rojox.core.test.repository

import com.android.rojox.core.local.db.impl.DbClient
import com.android.rojox.core.remote.PostService
import com.android.rojox.core.repository.PostRepositoryImpl
import com.android.rojox.core.test.utils.TestConstants
import com.android.rojox.core.test.utils.TestUtils
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class PostRepositoryTest {

    private lateinit var repository: PostRepositoryImpl
    private lateinit var postService: PostService
    private lateinit var dbClient: DbClient

    @Before
    fun setup() {
        postService = mock()
        dbClient = mock()
        repository = PostRepositoryImpl(postService, dbClient)
    }


    @Test
    fun `should return Post list on empty local Entities and Remote OK`() = runTest {

        //given
        val postsResponse = listOf(TestUtils.DEFAULT_POSTS_RESPONSE)
        whenever(dbClient.getPost()).thenReturn(emptyList())
        whenever(postService.getPosts()).thenReturn(postsResponse)

        //when:
        val post = repository.getPosts()


        verify(dbClient).savePosts(any())

        //then:
        assert(post.data != null) {
            post.error!!.message
        }
        assert(post.data!!.size == postsResponse.size)
    }

    @Test
    fun `should return Post list on local Entities found`() = runTest {

        //given
        val entities = listOf(TestUtils.DEFAULT_POST_ENTITY)
        whenever(dbClient.getPost()).thenReturn(entities)

        //when:
        val post = repository.getPosts()

        verify(dbClient, never()).savePosts(any())
        verify(postService, never()).getPosts()

        //then:
        assert(post.data != null) {
            post.error!!.message
        }
        assert(post.data!!.size == entities.size)
    }

    @Test
    fun `should return Comment list on empty local Entities and Remote OK`() = runTest {

        //given
        val commentsResponse = listOf(TestUtils.DEFAULT_COMMENT_RESPONSE)
        val postId = TestConstants.POST_ID
        whenever(dbClient.getComments(postId)).thenReturn(emptyList())
        whenever(postService.getComments(postId)).thenReturn(commentsResponse)

        //when:
        val post = repository.getComments(postId)


        verify(dbClient).saveComments(any())

        //then:
        assert(post.data != null) {
            post.error!!.message
        }
        assert(post.data!!.size == commentsResponse.size)
    }


    @Test
    fun `should return Comment list on local Entities found`() = runTest {

        //given
        val commentsEntities = listOf(TestUtils.DEFAULT_COMMENT_ENTITY)
        val postId = TestConstants.POST_ID
        whenever(dbClient.getComments(postId)).thenReturn(commentsEntities)
        whenever(postService.getComments(postId)).thenReturn(emptyList())

        //when:
        val post = repository.getComments(postId)


        verify(dbClient, never()).saveComments(any())
        verify(postService, never()).getComments(postId)

        //then:
        assert(post.data != null) {
            post.error!!.message
        }
        assert(post.data!!.size == commentsEntities.size)
    }

    @Test
    fun `should return Post on create Post`() = runTest {

        //given
        val title = TestConstants.POST_TITLE
        val body = TestConstants.POST_BODY
        val userId = TestConstants.USER_ID

        whenever(postService.postPost(argThat { it ->
            it.body == TestConstants.POST_BODY &&
            it.title == TestConstants.POST_TITLE
        })).thenReturn(TestUtils.DEFAULT_POSTS_RESPONSE)
        //when
        val post = repository.createPost(userId, title, body)

        assert(post.data != null) {
            post.error!!.message
        }

    }
}