package com.android.rojox.posttechhold.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.android.rojox.posttechhold.MainActivity
import com.android.rojox.posttechhold.ui.screens.ListPostScreen
import com.android.rojox.posttechhold.ui.screens.PostDetailScreen
import com.android.rojox.posttechhold.utils.composable


@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun HomeNavigation(activity: MainActivity) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavItem.Main.baseRoute
    ) {
        composable(NavItem.Main) {
            ListPostScreen(activity.viewModel, actions = NavigationActions {
                when (it) {
                    ActionType.CLICK_POST -> {
                        navController.navigate(NavItem.PostDetail.baseRoute)
                    }
                    else -> {
                    }
                }
            })
        }

        composable(NavItem.PostDetail) {
            PostDetailScreen(activity.viewModel, NavigationActions {
                navController.popBackStack()
            })
        }

        composable(NavItem.CreatePost) {

        }
    }
}