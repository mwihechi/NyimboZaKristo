package com.tansoften.nyimbozakristo.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asLiveData
import androidx.navigation.NavHostController
import com.tansoften.nyimbozakristo.DrawerScreens
import com.tansoften.nyimbozakristo.item.Songs
import com.tansoften.nyimbozakristo.model.SongsViewModel
import com.tansoften.nyimbozakristo.storage.SortOrder


@Composable
fun AllSongsScreen(
    openDrawer: () -> Unit,
    viewModel: SongsViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val songs = viewModel.songs.observeAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        AppBarAllSong(onButtonClicked = { openDrawer() }, viewModel = viewModel)

        SearchViewText(viewModel = viewModel)
        if (!songs.isNullOrEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.Center
            ) {
                items(songs) { song ->
                    SongsCard(song = song, viewModel = viewModel, navController)
                }
            }
        }
    }
}

@Composable
fun AppBarAllSong(
    onButtonClicked: () -> Unit,
    viewModel: SongsViewModel
) {

    val sortOrderPreference = viewModel.sortOrderPreferences.asLiveData().observeAsState().value

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .requiredHeight(50.dp)
            .fillMaxSize()
    ) {
        IconButton(onClick = {
            onButtonClicked()
        }) {
            Icon(Icons.Rounded.Menu, "Menu")
        }

        Text(text = "Nyimbo Za Kristo", textAlign = TextAlign.Center)

        if (sortOrderPreference != null) {
            IconButton(onClick = {
                when (sortOrderPreference.sortOrder) {
                    SortOrder.BY_NUMBER -> viewModel.sortOrderSelected(SortOrder.BY_NAME)
                    SortOrder.BY_NAME -> viewModel.sortOrderSelected(SortOrder.BY_NUMBER)
                }
            }) {
                when (sortOrderPreference.sortOrder) {
                    SortOrder.BY_NUMBER -> Icon(Icons.Default.Sort, "Sort")
                    SortOrder.BY_NAME -> Icon(Icons.Filled.SortByAlpha, "Sort by alpha")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SongsCard(song: Songs, viewModel: SongsViewModel, navController: NavHostController) {

    Card(
        modifier = Modifier
            .clickable {
                navController.navigate(route = DrawerScreens.VerseScreen.passPage(song.songs_id))
            }) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = song.songs_id.toString(),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = song.title,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )

            Surface(
                shape = CircleShape,
                onClick = {
                    viewModel.onLikeChecked(song = song)
                },
                modifier = Modifier
                    .size(30.dp)
            ) {
                when (song.like) {
                    true -> {
                        Icon(
                            Icons.Rounded.Favorite,
                            "Liked songs",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                    false -> {
                        Icon(Icons.TwoTone.Favorite, "Unliked Song")
                    }
                }
            }
        }
    }
}


@Composable
fun SearchViewText(viewModel: SongsViewModel) {
    var query by rememberSaveable { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextField(
            value = query,
            onValueChange = { onQueryChanged ->
                query = onQueryChanged
                if (onQueryChanged.isNotEmpty()) {
                    viewModel.searchQuery.value = query
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    tint = MaterialTheme.colors.onBackground,
                    contentDescription = "Search icon"
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = {
                        query = ""
                        viewModel.searchQuery.value = query
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            tint = MaterialTheme.colors.onBackground,
                            contentDescription = "Clear icon"
                        )
                    }
                }
            },
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            placeholder = { Text(text = "tafuta kwa maneno au namba") },
            textStyle = MaterialTheme.typography.subtitle1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}