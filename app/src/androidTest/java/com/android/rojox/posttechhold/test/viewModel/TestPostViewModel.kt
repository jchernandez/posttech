package com.android.rojox.posttechhold.test.viewModel

import com.android.rojox.core.model.DataState
import com.android.rojox.core.repository.PostRepository
import com.android.rojox.posttechhold.test.utils.MainCoroutineRule
import com.android.rojox.posttechhold.test.utils.TestConstants
import com.android.rojox.posttechhold.test.utils.TestUtils
import com.android.rojox.posttechhold.viewmodel.PostViewModel
import com.android.rojox.posttechhold.viewmodel.PostViewModelImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class TestPostViewModel {


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: PostViewModel
    private lateinit var repository: PostRepository

    @Before
    fun setUp() {
        repository = mock()
        viewModel = PostViewModelImpl(repository)
    }

    @Test
    fun shouldReturnPosts() = runTest {

        //given
        val posts = listOf(TestUtils.DEFAULT_POST)
        val isOnline = true
        viewModel.isOnline.value = isOnline

        //when

        viewModel.retrievePosts()
        `when`(repository.getPosts(!isOnline))
            .thenReturn(DataState.success(posts))
        //then
        advanceUntilIdle()
        assert(viewModel.posts.value?.data != null)
        verify(repository).getPosts(!isOnline)
    }

    @Test
    fun shouldReturnComments() = runTest {

        //given
        val comments = listOf(TestUtils.DEFAULT_COMMENT)
        val isOnline = true
        val post = TestUtils.DEFAULT_POST

        //when
        viewModel.selectedPost.value = post
        viewModel.isOnline.value = isOnline
        `when`(repository.getComments(post.id, !isOnline))
            .thenReturn(DataState.success(comments))
        viewModel.retrieveComments()

        //then
        advanceUntilIdle()
        assert(viewModel.comments.value?.data != null)
        verify(repository).getComments(post.id, !isOnline)
    }


    @Test
    fun shouldAddNewPost() = runTest {

        //given
        val title = TestConstants.POST_TITLE
        val body = TestConstants.POST_BODY
        val userId = TestConstants.USER_ID
        val post = TestUtils.DEFAULT_POST

        viewModel.newPostTitle.value = title
        viewModel.newPostBody.value = body

        //when
        `when`(repository.createPost(userId, title, body))
            .thenReturn(DataState.success(post))
        viewModel.addNewPost()

        //then
        advanceUntilIdle()
        assert(viewModel.newPost.value?.data != null)
        verify(repository).createPost(userId, title, body)
    }




}