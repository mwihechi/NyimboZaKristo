package com.mwihechi.nyimbozakristo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mwihechi.nyimbozakristo.screen.AboutScreen
import com.mwihechi.nyimbozakristo.screen.AllSongsScreen
import com.mwihechi.nyimbozakristo.screen.CategoryScreen
import com.mwihechi.nyimbozakristo.screen.LovedSongsScreen
import com.mwihechi.nyimbozakristo.screen.SettingScreen
import com.mwihechi.nyimbozakristo.screen.SplashScreen
import com.mwihechi.nyimbozakristo.screen.VerseScreen

@Composable
fun NavigationHost(navController: NavHostController, openDrawer: () -> Unit) {
    NavHost(
        navController = navController,
        startDestination = DrawerScreens.SplashScreen.route
    ) {
        composable(DrawerScreens.AllSongScreen.route) {
            AllSongsScreen(
                openDrawer = {
                    openDrawer()
                }, navController = navController,
                isShareApp = false
            )
        }

        composable(DrawerScreens.LovedSong.route) { LovedSongsScreen(navController = navController) }

        composable(DrawerScreens.CategoryScreen.route) { CategoryScreen(navController = navController) }

        composable(DrawerScreens.SettingScreen.route) { SettingScreen(navController = navController) }

        composable(DrawerScreens.AboutScreen.route) { AboutScreen(navController = navController) }

        composable(
            route = DrawerScreens.VerseScreen.route,
            arguments = listOf(
                navArgument(VERSE_ARGUMENT_KEY) {
                    type = NavType.IntType
                },
                navArgument(LOVE_ARGUMENT_KEY) {
                    type = NavType.BoolType
                }
            )
        ) {
            VerseScreen(
                navController = navController,
                page = it.arguments!!.getInt(VERSE_ARGUMENT_KEY),
                isLovedScreen = it.arguments!!.getBoolean(LOVE_ARGUMENT_KEY)
            )
        }

        composable(DrawerScreens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(DrawerScreens.ShareScreen.route) {
            AllSongsScreen(
                openDrawer = {
                    openDrawer()
                },
                navController = navController,
                isShareApp = true
            )
        }

    }
}