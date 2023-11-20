package com.android.rojox.posttechhold.viewmodel

import androidx.lifecycle.ViewModel
import com.android.rojox.core.model.Comment
import com.android.rojox.core.model.DataState
import com.android.rojox.core.model.Post
import kotlinx.coroutines.flow.*

abstract class PostViewModel: ViewModel() {

    abstract val posts: MutableStateFlow<DataState<List<Post>>?>
    abstract val newPost: MutableStateFlow<DataState<Post>?>
    abstract val comments: MutableStateFlow<DataState<List<Comment>>?>
    abstract val selectedPost: MutableStateFlow<Post?>

    abstract val newPostTitle: MutableStateFlow<String>
    abstract val newPostBody: MutableStateFlow<String>

    abstract val isNewPostBtnEnabled: StateFlow<Boolean>
    abstract val isDataLoading: StateFlow<Boolean>
    abstract val isCommentsLoading: StateFlow<Boolean>
    abstract val isOnline: MutableStateFlow<Boolean>

    abstract fun retrievePosts()
    abstract fun retrieveComments()
    abstract fun addNewPost()
    abstract fun resetNewPostData()
}