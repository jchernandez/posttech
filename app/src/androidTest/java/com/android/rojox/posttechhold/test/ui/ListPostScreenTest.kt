package com.android.rojox.posttechhold.test.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.rojox.core.model.Comment
import com.android.rojox.core.model.DataState
import com.android.rojox.core.model.Post
import com.android.rojox.posttechhold.App
import com.android.rojox.posttechhold.test.utils.TestConstants
import com.android.rojox.posttechhold.test.utils.TestUtils
import com.android.rojox.posttechhold.ui.navigation.NavigationActions
import com.android.rojox.posttechhold.ui.screens.ListPostScreen
import com.android.rojox.posttechhold.viewmodel.PostViewModel
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import com.android.rojox.posttechhold.R
import com.android.rojox.posttechhold.ui.navigation.ActionType
import junit.framework.TestCase.assertEquals

@RunWith(AndroidJUnit4::class)
@ExperimentalFoundationApi
class ListPostScreenTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: PostViewModel

    @Before
    fun setUp() {
        viewModel = mock()

    }

    @Test
    fun shouldShowPostList() = runTest {

        // Given
        val size = 10
        setUpValues(size)

        //when
        composeTestRule.setContent {
            ListPostScreen(viewModel, NavigationActions())
        }

        //then
        composeTestRule.onAllNodesWithText(TestConstants.POST_TITLE)
            .assertCountEquals(size)
    }

    @Test
    fun testClickFabAndShowSheet() {


        val context = ApplicationProvider.getApplicationContext<App>()


        // Given
        val size = 10
        setUpValues(size)

        composeTestRule.setContent {
            ListPostScreen(viewModel, NavigationActions())
        }

        //when
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.new_post))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.new_post))
            .performClick()

        //then shows bottom sheet
        composeTestRule
            .onNodeWithText(context.getString(R.string.new_post))
            .assertIsDisplayed()

    }


    @Test
    fun testClickPostAndSetNavigationAction() {

        // Given
        val size = 1
        setUpValues(size)

        var action: ActionType? = null
        composeTestRule.setContent {
            ListPostScreen(viewModel, NavigationActions {
                action = it
            })
        }

        //when
        composeTestRule
            .onNodeWithText(TestConstants.POST_TITLE)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(TestConstants.POST_TITLE)
            .performClick()

        //then
        assertEquals(ActionType.CLICK_POST, action)
    }


    private fun setUpValues(
        size: Int
    ) {

        val posts = mutableListOf<Post>().apply { repeat(size) { add(TestUtils.DEFAULT_POST) } }
        `when`(viewModel.retrievePosts()).then {  }
        `when`(viewModel.retrieveComments()).then {  }
        `when`(viewModel.isOnline).thenReturn(MutableStateFlow(false))
        `when`(viewModel.isDataLoading).thenReturn(MutableStateFlow(false))
        `when`(viewModel.isCommentsLoading).thenReturn(MutableStateFlow(false))
        `when`(viewModel.isNewPostBtnEnabled).thenReturn(MutableStateFlow(false))
        `when`(viewModel.posts).thenReturn(MutableStateFlow(DataState.success(posts)))
        `when`(viewModel.comments).thenReturn(MutableStateFlow(null as DataState<List<Comment>>?))
        `when`(viewModel.newPost).thenReturn(MutableStateFlow(null))
        `when`(viewModel.newPostTitle).thenReturn(MutableStateFlow(""))
        `when`(viewModel.newPostBody).thenReturn(MutableStateFlow(""))
        `when`(viewModel.selectedPost).thenReturn(MutableStateFlow(null))

    }




}
