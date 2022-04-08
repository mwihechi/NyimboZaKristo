package com.tansoften.nyimbozakristo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tansoften.nyimbozakristo.item.Songs
import com.tansoften.nyimbozakristo.view_model.SongsViewModel

@Composable
fun LovedSongsScreen(
    viewModel: SongsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val songViewModel = viewModel.lovedSongs.observeAsState()
    val songs = songViewModel.value

    Column(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(
            modifier = Modifier
                .requiredHeight(50.dp)
                .fillMaxSize()
        ) {

            val (arrowIcon, titleText) = createRefs()

            IconButton(onClick = {
                navController.popBackStack()
            }, modifier = Modifier.constrainAs(arrowIcon) {
                centerVerticallyTo(parent)
                start.linkTo(parent.start)
            }) {
                Icon(Icons.Filled.ArrowBack, "Menu")
            }
            Text(
                text = "Nyimbo Pendwa",
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(titleText) {
                    centerVerticallyTo(parent)
                    start.linkTo(arrowIcon.end)
                    end.linkTo(parent.end)
                },
                style = MaterialTheme.typography.h1
            )
        }
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
            songs.isNotEmpty() -> {
                LazyColumn {
                    itemsIndexed(songs) {index, song ->
                        SongsCard(
                            song = song,
                            viewModel = viewModel,
                            navController = navController,
                            index = index
                        )
                    }
                }
            }
        }
    }
}