package com.android.rojox.posttechhold

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.DisposableEffect
import coil.annotation.ExperimentalCoilApi
import com.android.rojox.posttechhold.ui.navigation.HomeNavigation
import com.android.rojox.posttechhold.utils.Utils

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {

    val viewModel = App.instance.getPostViewModel()

    @OptIn(ExperimentalCoilApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContent {
           val utils = Utils()
           viewModel.isOnline.value = utils.isNetworkAvailable(this)
           DisposableEffect(key1 = Unit, effect = {
               val connectivityManager = this@MainActivity
                   .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

               val callback = utils.connectivityCallback  {
                   viewModel.isOnline.value = it
               }
               connectivityManager.registerDefaultNetworkCallback(callback)

               onDispose {
                   connectivityManager.unregisterNetworkCallback(callback)
               }
           })

           HomeNavigation(activity = this)
       }
    }
}