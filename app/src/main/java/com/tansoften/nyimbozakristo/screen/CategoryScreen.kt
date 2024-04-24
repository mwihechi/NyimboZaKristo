package com.tansoften.nyimbozakristo.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.tansoften.nyimbozakristo.DrawerScreens


@Composable
fun CategoryScreen(navController: NavHostController) {
    BackHandler {
        navController.navigate(DrawerScreens.AllSongScreen.route)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        DefaultAppBar(
            title = "Yaliyomo",
            buttonIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onButtonClicked = { navController.navigate(DrawerScreens.AllSongScreen.route) })

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Kipengele hiki bado kipo kwenye matengenezo",
                textAlign = TextAlign.Center
            )
        }

    }
}