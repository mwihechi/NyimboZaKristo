package com.mwihechi.nyimbozakristo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.mwihechi.nyimbozakristo.component.Drawer
import com.mwihechi.nyimbozakristo.component.NavigationHost
import com.mwihechi.nyimbozakristo.component.SetBarColors
import com.mwihechi.nyimbozakristo.ui.theme.NyimboZaKristoTheme
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
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppMainScreen()
                }
            }
        }
    }
}


@Composable
fun AppMainScreen() {
    SetBarColors()
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colorScheme.background) {
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
            NavigationHost(navController = navController, openDrawer = { openDrawer() })
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