package com.tansoften.nyimbozakristo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tansoften.nyimbozakristo.screen.*
import com.tansoften.nyimbozakristo.ui.theme.NyimboZaKristoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NyimboZaKristoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppMainScreen()
                }
            }
        }
    }
}


@Composable
fun AppMainScreen() {
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colors.background) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val openDrawer = {
            scope.launch {
                drawerState.open()
            }
        }
        ModalDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                Drawer(
                    onDestinationClicked = { route ->
                        scope.launch {
                            drawerState.close()
                        }
                        navController.navigate(route) {
                            popUpTo(route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        ) {
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

                composable(DrawerScreens.ShareScreen.route) {}

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NyimboZaKristoTheme {
        AppMainScreen()
    }
}