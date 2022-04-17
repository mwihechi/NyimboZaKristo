package com.tansoften.nyimbozakristo.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.tansoften.nyimbozakristo.DrawerScreens


@Composable
fun AboutScreen(navController: NavController) {
    val context = LocalContext.current
    val manager = context.packageManager.getPackageInfo(context.packageName, 0)

    BackHandler {
        navController.navigate(DrawerScreens.AllSongScreen.route)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        DefaultAppBar(
            title = "Kuhusu",
            buttonIcon = Icons.Filled.ArrowBack,
            onButtonClicked = { navController.navigate(DrawerScreens.AllSongScreen.route) })


        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            // Text(text = "Nyimbo Za Kristo.")
            Text(text = "Toleo Namba: ${manager.versionName}")
        }
    }

}

