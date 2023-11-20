package com.android.rojox.posttechhold.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

import com.android.rojox.posttechhold.ui.navigation.NavItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


fun NavGraphBuilder.composable(
    navItem: NavItem,
    content: @Composable (NavBackStackEntry) -> Unit) {
    composable(
        route= navItem.baseRoute,
    ) {
        content(it)
    }
}


@Composable
fun <T> Flow<T>.observeAsActions(onEach: (T) -> Unit) {
    val flow = this
    LaunchedEffect(key1 = flow) {
        flow.onEach(onEach).collect()
    }
}