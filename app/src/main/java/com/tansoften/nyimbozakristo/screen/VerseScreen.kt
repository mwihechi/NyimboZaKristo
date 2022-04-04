package com.tansoften.nyimbozakristo.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.tansoften.nyimbozakristo.item.Songs
import com.tansoften.nyimbozakristo.storage.SortOrder
import com.tansoften.nyimbozakristo.ui.theme.LikeColor
import com.tansoften.nyimbozakristo.view_model.SongsViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun VerseScreen(
    navController: NavController,
    page: Int,
    viewModel: SongsViewModel = hiltViewModel()
) {

    val verse = viewModel.verses.observeAsState().value
    val sortOrderPreference = viewModel.sortOrderPreferences.asLiveData().observeAsState().value


    if (sortOrderPreference != null && verse != null) {
        when (sortOrderPreference.sortOrder) {
            SortOrder.BY_NUMBER -> {
                Content(
                    navController = navController,
                    page = page,
                    viewModel = viewModel,
                    verse = verse
                )
            }

            SortOrder.BY_NAME -> {
                val songSorted = verse.sortedBy { songs->
                    songs.title
                }
                Content(
                    navController = navController,
                    page = page,
                    viewModel = viewModel,
                    verse = songSorted
                )
            }
        }
    }


}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Content(
    navController: NavController,
    page: Int,
    viewModel: SongsViewModel,
    verse: List<Songs>,
) {
    val pagerState = rememberPagerState()
    var title by remember { mutableStateOf("") }
    var pageNo by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .requiredHeight(50.dp)
                .fillMaxSize()
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBack, "Menu")
            }
            Text(
                text = "${verse[pageNo].songs_id} ${verse[pageNo].title}",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                style = MaterialTheme.typography.h1
            )
            IconButton(onClick = {
                viewModel.onLikeChecked(song = verse[pageNo])
            }) {
                when (verse[pageNo].like) {
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


        HorizontalPager(
            count = 220,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) { page ->
            val text =
                HtmlCompat.fromHtml(
                    verse[page].verse_text,
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                )
            Text(text = text.toString(), textAlign = TextAlign.Center)
        }

        LaunchedEffect(pagerState) {
            pagerState.scrollToPage(page)
            snapshotFlow { pagerState.currentPage }.collect { page ->
                pageNo = page
            }
        }
    }
}