package com.tansoften.nyimbozakristo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
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

    Column(modifier = Modifier.fillMaxSize()) {
        AppBarAllSong(onButtonClicked = { openDrawer() }, viewModel = viewModel)

        SearchViewText(viewModel = viewModel)
        if (!songs.isNullOrEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.Center
            ) {
                itemsIndexed(songs) { index, song ->
                    SongsCard(song = song, viewModel = viewModel, navController, index = index)
                }
            }
        }

       /* if (isShareApp) {
            ShareApp()
        }*/
    }
}

@Composable
fun AppBarAllSong(
    onButtonClicked: () -> Unit,
    viewModel: SongsViewModel
) {
    // sort order value
    val sortOrderPreference = viewModel.songSorted.observeAsState().value

    ConstraintLayout(
        modifier = Modifier
            .requiredHeight(50.dp)
            .fillMaxSize()
    ) {
        val (leadingIcon, textTitle, landingIcon) = createRefs()

        createHorizontalChain(
            leadingIcon,
            textTitle,
            landingIcon,
            chainStyle = ChainStyle.SpreadInside
        )

        IconButton(onClick = {
            onButtonClicked()
        }, modifier = Modifier.constrainAs(leadingIcon) {
            centerVerticallyTo(parent)
        }) {
            Icon(Icons.Filled.Menu, "Menu")
        }

        Text(
            text = "Nyimbo Za Kristo", textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1,
            modifier = Modifier.constrainAs(textTitle) {
                centerVerticallyTo(parent)
            }
        )

        if (sortOrderPreference != null) {
            IconButton(modifier = Modifier.constrainAs(landingIcon) {
                centerVerticallyTo(parent)
            }, onClick = {
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
fun SongsCard(
    song: Songs,
    viewModel: SongsViewModel,
    navController: NavHostController,
    index: Int
) {

    Card(
        modifier = Modifier
            .clickable {
                if (viewModel.searchQuery.value.isEmpty()) {
                    navController.navigate(route = DrawerScreens.VerseScreen.passPage(index))
                } else {
                    val index2 = song.songs_id - 1
                    navController.navigate(route = DrawerScreens.VerseScreen.passPage(index2))
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