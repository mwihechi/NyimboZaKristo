package com.tansoften.nyimbozakristo.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tansoften.nyimbozakristo.DrawerScreens
import com.tansoften.nyimbozakristo.item.Songs
import com.tansoften.nyimbozakristo.storage.SortOrder
import com.tansoften.nyimbozakristo.ui.theme.LikeColor
import com.tansoften.nyimbozakristo.view_model.SongsViewModel


@Composable
fun AllSongsScreen(
    openDrawer: () -> Unit,
    viewModel: SongsViewModel = hiltViewModel(),
    navController: NavHostController,
    isShareApp: Boolean
) {
    val songs = viewModel.songs.observeAsState().value
    val activity = (LocalContext.current as? Activity)

    BackHandler {
        activity?.finish()
    }


    Column(modifier = Modifier.fillMaxSize()) {
        AppBarAllSong(onButtonClicked = { openDrawer() }, viewModel = viewModel)

        SearchViewText(viewModel = viewModel)
        if (!songs.isNullOrEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.Center
            ) {
                itemsIndexed(songs) { index, song ->
                    SongsCard(
                        song = song,
                        viewModel = viewModel,
                        navController,
                        index = index,
                        isLovedScreen = false
                    )
                }
            }
        }

        if (isShareApp) {
            ShareApp()
        }
    }
}

@Composable
fun AppBarAllSong(
    onButtonClicked: () -> Unit,
    viewModel: SongsViewModel
) {
    // sort order value
    val sortOrderPreference = viewModel.songSorted.observeAsState().value

    TopAppBar(
        title = {
            Text(text = "Nyimbo Za Kristo")
        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
                Icon(Icons.Filled.Menu, "Menu")
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            if (sortOrderPreference != null) {
                IconButton(onClick = {
                    when (sortOrderPreference.sortOrder) {
                        SortOrder.BY_NUMBER -> viewModel.sortOrderSelected(SortOrder.BY_NAME)
                        SortOrder.BY_NAME -> viewModel.sortOrderSelected(SortOrder.BY_NUMBER)
                    }
                }) {
                    when (sortOrderPreference.sortOrder) {
                        SortOrder.BY_NUMBER -> Icon(Icons.AutoMirrored.Filled.Sort, "Sort")
                        SortOrder.BY_NAME -> Icon(Icons.Filled.SortByAlpha, "Sort by alpha")
                    }
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SongsCard(
    song: Songs,
    viewModel: SongsViewModel,
    navController: NavHostController,
    index: Int,
    isLovedScreen: Boolean
) {

    Card(
        modifier = Modifier
            .clickable {
                if (viewModel.searchQuery.value.isEmpty()) {
                    navController.navigate(
                        route = DrawerScreens.VerseScreen.passArgument(
                            currentPage = index,
                            isLovedScreen = isLovedScreen
                        )
                    )
                } else {
                    val index2 = song.songs_id - 1
                    navController.navigate(
                        route = DrawerScreens.VerseScreen.passArgument(
                            currentPage = index2,
                            isLovedScreen = isLovedScreen
                        )
                    )
                }

            }) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            val (textTitle, textNo, landingIcon) = createRefs()
            val firstGuideLine = createGuidelineFromAbsoluteLeft(0.11f)
            val secondGuideLine = createGuidelineFromStart(0.9f)

            Text(
                text = song.songs_id.toString(),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,

                modifier = Modifier
                    .background(MaterialTheme.colors.background, shape = CircleShape)
                    .constrainAs(textNo) {
                        linkTo(parent.start, firstGuideLine)
                        top.linkTo(parent.top)
                    }
                    .badgeLayout()
            )
            Text(
                text = song.title,
                fontSize = 18.sp,
                maxLines = 1,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(textTitle) {
                        linkTo(firstGuideLine, secondGuideLine, startMargin = 2.dp)
                        width = Dimension.fillToConstraints
                    }
            )

            Surface(
                shape = CircleShape,
                onClick = {
                    viewModel.onLikeChecked(song = song)
                },
                modifier = Modifier
                    .size(26.dp)
                    .constrainAs(landingIcon) {
                        linkTo(secondGuideLine, parent.end)
                        width = Dimension.preferredWrapContent
                    }
            ) {
                when (song.like) {
                    true -> {
                        Icon(
                            Icons.Rounded.Favorite,
                            "Liked songs",
                            tint = LikeColor
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


fun Modifier.badgeLayout() =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        // based on the expectation of only one line of text
        val minPadding = placeable.height / 4

        val width = maxOf(placeable.width + minPadding, placeable.height)
        layout(width, placeable.height) {
            placeable.place((width - placeable.width) / 2, 0)
        }
    }