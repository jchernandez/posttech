package com.android.rojox.posttechhold.ui.navigation

class NavigationActions (
    val onAction: (type: ActionType) -> Unit = {}
)

enum class ActionType {
    CLICK_POST,
    BACK,
    CREATE_POST
}