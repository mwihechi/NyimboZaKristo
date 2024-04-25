package com.mwihechi.nyimbozakristo.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mwihechi.nyimbozakristo.DrawerScreens
import com.mwihechi.nyimbozakristo.item.Songs
import com.mwihechi.nyimbozakristo.view_model.SongsViewModel

@Composable
fun LovedSongsScreen(
    viewModel: SongsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val songViewModel = viewModel.lovedSongs.observeAsState()
    val songs = songViewModel.value

    BackHandler {
        navController.navigate(DrawerScreens.AllSongScreen.route)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        DefaultAppBar(
            title = "Nyimbo Pendwa",
            buttonIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onButtonClicked = { navController.navigate(DrawerScreens.AllSongScreen.route) })
        Content(songs = songs, viewModel = viewModel, navController = navController)
    }

}


@Composable
fun Content(songs: List<Songs>?, viewModel: SongsViewModel, navController: NavHostController) {
    if (songs != null) {
        when {
            songs.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Huna nyimbo pendwa kwa sasa.", fontSize = 18.sp)
                }

            }

            else -> {
                LazyColumn {
                    itemsIndexed(songs) { index, song ->
                        SongsCard(
                            song = song,
                            viewModel = viewModel,
                            navController = navController,
                            index = index,
                            isLovedScreen = true
                        )
                    }
                }
            }
        }
    }
}