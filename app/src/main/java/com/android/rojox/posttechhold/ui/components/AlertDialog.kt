package com.android.rojox.posttechhold.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.android.rojox.posttechhold.R
import com.android.rojox.posttechhold.utils.UiError

@Composable
fun ProgressDialog(message: String = stringResource(id = R.string.loading)) {
    Dialog(
        onDismissRequest = { },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun AlertMessage(error: UiError, onClickAction: () -> Unit) {
    AlertDialog(onDismissRequest = {  },
        text = {Text(text =  error.message)},
        confirmButton = {
            Button(onClick = onClickAction) {
                Text(text = error.positiveAction!!)
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false)
    )
}