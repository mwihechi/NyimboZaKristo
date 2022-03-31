package com.tansoften.nyimbozakristo.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tansoften.nyimbozakristo.model.SongsViewModel

@Composable
fun LovedSongsScreen(
    viewModel: SongsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val songViewModel = viewModel.lovedSongs.observeAsState()
    val songs = songViewModel.value



    if (songs != null) {
        when {
            songs.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Huna nyimbo pendwa kwa sasa", fontSize = 18.sp)
                }

            }
            songs.isNotEmpty() -> {
                LazyColumn {
                    items(songs) { song ->
                        SongsCard(
                            song = song,
                            viewModel = viewModel,
                            navController = navController
                        )
                    }
                }
            }
        }

    }
}