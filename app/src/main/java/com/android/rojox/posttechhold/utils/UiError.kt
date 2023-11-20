package com.android.rojox.posttechhold.utils

data class UiError(
    var message: String = "",
    var positiveAction: String? = null,
    var onAction: () -> Unit = {}
)