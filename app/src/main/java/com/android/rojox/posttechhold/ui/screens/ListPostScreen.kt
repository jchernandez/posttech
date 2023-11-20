package com.android.rojox.posttechhold.ui.screens

import android.content.Context
import android.os.Handler
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.android.rojox.core.model.DataState
import com.android.rojox.core.model.DataStatus
import com.android.rojox.core.model.Post
import com.android.rojox.posttechhold.R
import com.android.rojox.posttechhold.di.AppBusiness
import com.android.rojox.posttechhold.ui.navigation.ActionType
import com.android.rojox.posttechhold.ui.UiApp
import com.android.rojox.posttechhold.ui.components.AlertMessage
import com.android.rojox.posttechhold.ui.components.ProgressDialog
import com.android.rojox.posttechhold.ui.navigation.NavigationActions
import com.android.rojox.posttechhold.ui.components.TextField
import com.android.rojox.posttechhold.utils.ComposableLifecycle
import com.android.rojox.posttechhold.utils.UiError
import com.android.rojox.posttechhold.utils.Utils
import com.android.rojox.posttechhold.utils.observeAsActions
import com.android.rojox.posttechhold.viewmodel.PostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListPostScreen(
    viewModel: PostViewModel,
    actions: NavigationActions = NavigationActions()
) {

    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val isLoading by viewModel.isDataLoading.collectAsState()
    val context = LocalContext.current

    var uiError: UiError? by remember {
        mutableStateOf(null)
    }

    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            viewModel.retrievePosts()
        }
    }

    viewModel.posts.observeAsActions(onEach = {
        if (it?.status == DataStatus.ERROR){
            uiError = UiError(
                it.error!!.message,
                context.getString(R.string.accept)
            ) {
                viewModel.retrievePosts()
            }
        }
    })

    viewModel.newPost.observeAsActions(onEach = {
        if (it?.status == DataStatus.SUCCESS) {
            onPostCreated(context, viewModel,
                coroutineScope, sheetState)
        } else if (it?.status == DataStatus.ERROR){
            uiError = UiError(
                it.error!!.message,
                context.getString(R.string.accept))
        }
    })

    if (!sheetState.isVisible) {
        viewModel.resetNewPostData()
    }

    if (uiError != null) {
        AlertMessage(error = uiError!!) {
            uiError!!.onAction()
            uiError = null
        }
    }

    UiApp {

        if (isLoading) {
            ProgressDialog()
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    backgroundColor = MaterialTheme.colors.primary,
                )
            },
            content = {
                Content(
                    viewModel,
                    sheetState,
                    onPostSelected = {
                        actions.onAction(ActionType.CLICK_POST)
                    },
                    onClickNewPost = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    })
            }
        )
    }

}

@OptIn(ExperimentalMaterialApi::class)
private fun onPostCreated(
    context: Context,
    viewModel: PostViewModel,
    coroutineScope: CoroutineScope,
    sheetState: ModalBottomSheetState
) {
    coroutineScope.launch { sheetState.hide() }
    viewModel.retrievePosts()
    Toast.makeText(context,
        context.getString(R.string.added), Toast.LENGTH_SHORT).show()
    viewModel.resetNewPostData()
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Content(
    viewModel: PostViewModel,
    sheetState: ModalBottomSheetState,
    onPostSelected: ()-> Unit,
    onClickNewPost: () -> Unit
) {

    val posts by viewModel.posts.collectAsState()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {
            PostCreateSheet(viewModel, onClickCreated = {
                viewModel.addNewPost()
            })
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()) {
            LazyColumn {
                if (posts?.data != null ) {
                    items(posts!!.data!!) {
                        PostItem(post = it) {
                            viewModel.selectedPost.value = it
                            onPostSelected()
                        }
                        Divider()
                    }
                    item {
                        Box(modifier = Modifier.padding(bottom = 80.dp))
                    }
                }
            }
            FloatingActionButton(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
                    .background(MaterialTheme.colors.primary, CircleShape),
                onClick = onClickNewPost
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.new_post))
            }
        }
    }
}

@Composable
private fun PostCreateSheet(
    viewModel: PostViewModel,
    onClickCreated: () -> Unit
) {

    val title by viewModel.newPostTitle.collectAsState()
    val body by viewModel.newPostBody.collectAsState()
    val isBtnEnabled by viewModel.isNewPostBtnEnabled.collectAsState()



    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = stringResource(id = R.string.new_post))
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    onValueChange = {viewModel.newPostTitle.value = it},
                    placeholder = stringResource(id = R.string.title),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = body,
                    onValueChange = {viewModel.newPostBody.value = it},
                    placeholder = stringResource(id = R.string.body),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isBtnEnabled,
                    onClick = onClickCreated) {
                    Text(text = stringResource(id = R.string.add_post))
                }
            }
        }
    }
}


@Composable
fun PostItem(post: Post, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onClick()
            }
            .padding(10.dp)
    ) {
        Text(
            text = post.title,
            style = MaterialTheme.typography.body1
        )
    }
}

@ExperimentalFoundationApi
@Composable
@Preview
fun PreviewListPostScreen() {
    val viewModel = AppBusiness(
        LocalContext.current).getPostViewModel()
    ListPostScreen(viewModel)
    Handler().postDelayed({
        val items = arrayListOf<Post>()
        for (i in 0 until 10) {
            items.add(
                Post(
                i,
                i,
                "Post $i",
                "Body post $i"
            )
            )
        }
        viewModel.posts.value = DataState.success(items)
    }, TimeUnit.SECONDS.toMillis(2L))
}

@ExperimentalFoundationApi
@Composable
@Preview
fun PreviewSheet() {
    val viewModel = AppBusiness(
        LocalContext.current).getPostViewModel()
    PostCreateSheet(viewModel, {})

}