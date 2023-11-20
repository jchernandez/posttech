package com.android.rojox.posttechhold.viewmodel

import androidx.lifecycle.viewModelScope
import com.android.rojox.core.model.Comment
import com.android.rojox.core.model.DataState
import com.android.rojox.core.model.DataStatus
import com.android.rojox.core.model.Post
import com.android.rojox.core.repository.PostRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PostViewModelImpl(private val repository: PostRepository): PostViewModel() {

    override val posts = MutableStateFlow(null as DataState<List<Post>>?)
    override val newPost = MutableStateFlow(null as DataState<Post>?)
    override val comments = MutableStateFlow(null as DataState<List<Comment>>?)
    override val selectedPost = MutableStateFlow(null as Post?)

    override val newPostTitle = MutableStateFlow("")
    override val newPostBody = MutableStateFlow("")

    override val isNewPostBtnEnabled: StateFlow<Boolean> = combine(newPostBody, newPostTitle) { body, title ->
        body.isNotEmpty() && body.isNotBlank() && title.isNotBlank() && title.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)


    override val isDataLoading: StateFlow<Boolean> = combine(posts, newPost) { post, comments ->
        post?.status == DataStatus.LOADING || comments?.status == DataStatus.LOADING
    }.combine(newPost){ combine, newPost ->
        combine || newPost?.status == DataStatus.SUCCESS
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)


    override val isCommentsLoading: StateFlow<Boolean> = comments.map {
        it?.status == DataStatus.LOADING
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    override val isOnline = MutableStateFlow(true)

    override fun retrievePosts() {
        posts.value = DataState()
        viewModelScope.launch {
            posts.value = repository.getPosts(!isOnline.value)
        }
    }

    override fun retrieveComments() {
        comments.value = DataState()
        viewModelScope.launch {
            comments.value = repository.getComments(
                selectedPost.value!!.id, !isOnline.value
            )
        }
    }

    override fun addNewPost() {
        newPost.value = DataState()
        viewModelScope.launch {
            newPost.value = repository.createPost(
                1, newPostTitle.value, newPostBody.value)
        }
    }

    override fun resetNewPostData() {
        newPostBody.value =""
        newPostTitle.value =""
        newPost.value = null
    }
}