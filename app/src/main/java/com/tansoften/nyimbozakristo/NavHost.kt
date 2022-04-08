package com.tansoften.nyimbozakristo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tansoften.nyimbozakristo.screen.*

@Composable
fun NavigationHost(navController: NavHostController, openDrawer: () -> Unit) {
    NavHost(
        navController = navController,
        startDestination = DrawerScreens.AllSongScreen.route
    ) {
        composable(DrawerScreens.AllSongScreen.route) {
            AllSongsScreen(openDrawer = {
                openDrawer()
            }, navController = navController)
        }

        composable(DrawerScreens.LovedSong.route) { LovedSongsScreen(navController = navController) }

        composable(DrawerScreens.CategoryScreen.route) { CategoryScreen(navController = navController) }

        composable(DrawerScreens.SettingScreen.route) { SettingScreen(navController = navController) }

        composable(DrawerScreens.AboutScreen.route) { AboutScreen(navController = navController) }

        composable(
            route = DrawerScreens.VerseScreen.route,
            arguments = listOf(navArgument(VERSE_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {
            VerseScreen(
                navController = navController,
                it.arguments!!.getInt(VERSE_ARGUMENT_KEY)
            )
        }

        //composable(DrawerScreens.ShareScreen.route) { ShareApp() }

    }
}