package com.android.rojox.posttechhold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import coil.annotation.ExperimentalCoilApi
import com.android.rojox.posttechhold.ui.navigation.HomeNavigation

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {

    val viewModel = App.instance.getPostViewModel()

    @OptIn(ExperimentalCoilApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContent {
           HomeNavigation(activity = this)
       }
    }
}