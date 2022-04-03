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
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.tansoften.nyimbozakristo.model.SongsViewModel
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalPagerApi::class, androidx.compose.material.ExperimentalMaterialApi::class)
@Composable
fun VerseScreen(
    navController: NavController,
    page: Int,
    viewModel: SongsViewModel = hiltViewModel()
) {
    val verse = viewModel.verses.observeAsState().value
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
                text = title,
                textAlign = TextAlign.Center
            )
            IconButton(onClick = {
                viewModel.onLikeChecked(song = verse!![pageNo])
            }) {
                when (verse?.get(pageNo)?.like) {
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
                    else -> {}
                }
            }
        }

        if (!verse.isNullOrEmpty()) {
            HorizontalPager(
                count = 220,
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
            ) { page ->
                val text =
                    HtmlCompat.fromHtml(
                        verse[page].verse_text,
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )
                Text(text = text.toString(), textAlign = TextAlign.Center, fontSize = 16.sp)
            }

            LaunchedEffect(pagerState) {
                pagerState.scrollToPage(page - 1)
                snapshotFlow { pagerState.currentPage }.collect { page ->
                    pageNo = page
                    title = verse[page].title
                }
            }
        }
    }
}