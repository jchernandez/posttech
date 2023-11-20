package com.android.rojox.posttechhold.ui.navigation

sealed class NavItem (val baseRoute: String) {
    object  Main: NavItem("main")
    object  PostDetail: NavItem("postDetail")
    object  CreatePost: NavItem("createPost")
}