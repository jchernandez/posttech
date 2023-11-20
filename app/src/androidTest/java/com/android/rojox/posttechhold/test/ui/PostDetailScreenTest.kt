package com.android.rojox.posttechhold.test.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import com.android.rojox.core.model.DataState
import com.android.rojox.posttechhold.App
import com.android.rojox.posttechhold.test.utils.TestConstants
import com.android.rojox.posttechhold.test.utils.TestUtils
import com.android.rojox.posttechhold.ui.navigation.NavigationActions
import com.android.rojox.posttechhold.ui.screens.PostDetailScreen
import com.android.rojox.posttechhold.viewmodel.PostViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import com.android.rojox.posttechhold.R
import com.android.rojox.posttechhold.ui.navigation.ActionType
import junit.framework.TestCase.assertEquals

@ExperimentalFoundationApi
class PostDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: PostViewModel

    @Before
    fun setUp() {
        viewModel = mock()

    }


    @Test
    fun shouldShowPostDetailWithComments() = runTest {

        // Given
        setUpValues()

        //when
        composeTestRule.setContent {
            PostDetailScreen(viewModel, NavigationActions())
        }

        //then
        composeTestRule.onAllNodesWithText(TestConstants.POST_TITLE)
            .assertCountEquals(2)
        composeTestRule.onAllNodesWithText(TestConstants.POST_BODY)
            .assertCountEquals(1)
        composeTestRule.onAllNodesWithText(TestConstants.COMMENT_BODY)
            .assertCountEquals(1)
    }


    @Test
    fun shouldSetActionOnBackToolbar() = runTest {

        val context = ApplicationProvider.getApplicationContext<App>()

        // Given
        setUpValues()
        var action: ActionType? = null

        //when
        composeTestRule.setContent {
            PostDetailScreen(viewModel, NavigationActions{
                action = it
            })
        }

        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.back)
        ).assertIsDisplayed()
            .performClick()

        //then
        assertEquals(ActionType.BACK, action)


    }

    private fun setUpValues() {

        val posts = mutableListOf(TestUtils.DEFAULT_POST)
        val comments = mutableListOf(TestUtils.DEFAULT_COMMENT)
        `when`(viewModel.isOnline).thenReturn(MutableStateFlow(false))
        `when`(viewModel.isDataLoading).thenReturn(MutableStateFlow(false))
        `when`(viewModel.isCommentsLoading).thenReturn(MutableStateFlow(false))
        `when`(viewModel.isNewPostBtnEnabled).thenReturn(MutableStateFlow(false))
        `when`(viewModel.posts).thenReturn(MutableStateFlow(DataState.success(posts)))
        `when`(viewModel.comments).thenReturn(MutableStateFlow(DataState.success(comments)))
        `when`(viewModel.newPost).thenReturn(MutableStateFlow(null))
        `when`(viewModel.newPostTitle).thenReturn(MutableStateFlow(""))
        `when`(viewModel.newPostBody).thenReturn(MutableStateFlow(""))
        `when`(viewModel.selectedPost).thenReturn(MutableStateFlow(posts[0]))
        `when`(viewModel.retrievePosts()).then {  }
        `when`(viewModel.retrieveComments()).then {  }
    }
}