package com.android.rojox.posttechhold.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.android.rojox.core.model.Comment
import com.android.rojox.core.model.DataState
import com.android.rojox.core.model.DataStatus
import com.android.rojox.core.model.Post
import com.android.rojox.posttechhold.R
import com.android.rojox.posttechhold.di.AppBusiness
import com.android.rojox.posttechhold.ui.navigation.ActionType
import com.android.rojox.posttechhold.ui.navigation.NavigationActions
import com.android.rojox.posttechhold.utils.ComposableLifecycle
import com.android.rojox.posttechhold.ui.UiApp
import com.android.rojox.posttechhold.ui.components.AlertMessage
import com.android.rojox.posttechhold.utils.UiError
import com.android.rojox.posttechhold.utils.observeAsActions
import com.android.rojox.posttechhold.viewmodel.PostViewModel

@Composable
fun PostDetailScreen(viewModel: PostViewModel, actions: NavigationActions = NavigationActions()) {

    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            viewModel.retrieveComments()
        }
    }

    val post = viewModel.selectedPost.value!!
    val comments by viewModel.comments.collectAsState()
    val isLoading by viewModel.isCommentsLoading.collectAsState()
    val context = LocalContext.current
    var uiError: UiError? by remember {
        mutableStateOf(null)
    }

    viewModel.posts.observeAsActions(onEach = {
        if (it?.status == DataStatus.ERROR) {
            uiError = UiError(
                it.error!!.message,
                context.getString(R.string.accept))
        }
    })

    if (uiError != null) {
        AlertMessage(error = uiError!!) {
            uiError!!.onAction()
            uiError = null
        }
    }

    UiApp {
        Scaffold(
            topBar = {
                PostTopBar(
                    post,
                    onClickBack = {
                        actions.onAction(ActionType.BACK)
                    })
            },
            content = {
                Content(post = post, isLoading = isLoading, comments = comments?.data)
            }
        )
    }
}

@Composable
fun PostTopBar(post: Post, onClickBack:() -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = post.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            ) },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = {
            IconButton(onClick = onClickBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription =  stringResource(id = R.string.back))
            }
        },
    )
}


@Composable
fun Content(post: Post, isLoading: Boolean, comments: List<Comment>?) {
    LazyColumn {
        item {
            PostContent(post)
            Divider()
        }

        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (comments != null) {
                    Column {
                        comments.forEach {
                            CommentItem(comment = it)
                            Divider()
                        }
                    }
                }
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
        }
    }

}

@Composable
fun PostContent(post: Post) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = post.title,
            style = MaterialTheme.typography.h5,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = post.body,
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(17.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = comment.email,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.primary
        )

        Text(
            text = comment.name,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.primary
        )

        Text(
            modifier = Modifier.padding(top = 5.dp),
            text = comment.body,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@ExperimentalFoundationApi
@Composable
@Preview
fun PreviewPostDetailScreen() {
    val viewModel = AppBusiness(LocalContext.current).getPostViewModel()
    viewModel.selectedPost.value = Post(1, 1, "title", "body, ")

    PostDetailScreen(viewModel = viewModel, NavigationActions {
        viewModel.comments.value = DataState.success(
            listOf(Comment(1, "bbjbjbjb", "sadsad", "!31"))
        )
    })
}

@ExperimentalFoundationApi
@Composable
@Preview
fun PreviewComment() {
    CommentItem(
        Comment(1,
        "name",
        "email@email",
        "body as body")
    )
}