package com.mwihechi.nyimbozakristo.screen

import android.content.Context
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mwihechi.nyimbozakristo.APP_URL
import com.mwihechi.nyimbozakristo.DrawerScreens
import com.mwihechi.nyimbozakristo.item.Songs
import com.mwihechi.nyimbozakristo.storage.SortOrder
import com.mwihechi.nyimbozakristo.ui.theme.LikeColor
import com.mwihechi.nyimbozakristo.view_model.VersesViewModel
import kotlin.math.roundToInt


@Composable
fun VerseScreen(
    navController: NavController,
    page: Int,
    viewModel: VersesViewModel = hiltViewModel(),
    isLovedScreen: Boolean
) {

    val verse = viewModel.verses.observeAsState().value
    val sortOrderPreference = viewModel.sortOrder.observeAsState().value
    val font = viewModel.fontSize.observeAsState().value
    val isScreenOn = viewModel.isScreenOn.observeAsState().value
    val lovedVerses = viewModel.lovedVerses.observeAsState().value

    if (isLovedScreen && lovedVerses != null && font != null) {
        val fontSize = (font * 100).roundToInt()
        when {
            lovedVerses.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Huna nyimbo pendwa kwa sasa.", fontSize = 18.sp)
                }

            }

            else -> {
                Content(
                    navController = navController,
                    page = page,
                    viewModel = viewModel,
                    verse = lovedVerses,
                    fontSize = fontSize,
                )
            }
        }
        BackHandler {
            navController.navigate(DrawerScreens.AllSongScreen.route)
        }

    } else {
        if (sortOrderPreference != null && verse != null && font != null) {
            val fontSize = (font * 100).roundToInt()
            when (sortOrderPreference.sortOrder) {
                SortOrder.BY_NUMBER -> {
                    Content(
                        navController = navController,
                        page = page,
                        viewModel = viewModel,
                        verse = verse,
                        fontSize = fontSize
                    )
                }

                SortOrder.BY_NAME -> {

                    val verseSorted = verse.sortedBy { verses -> verses.title }

                    Content(
                        navController = navController,
                        page = page,
                        viewModel = viewModel,
                        verse = verseSorted,
                        fontSize = fontSize
                    )
                }
            }
        }
    }

    if (isScreenOn == true) {
        KeepScreenOn()
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Content(
    navController: NavController,
    page: Int,
    viewModel: VersesViewModel,
    verse: List<Songs>,
    fontSize: Int,
) {
    val pagerState =
        androidx.compose.foundation.pager.rememberPagerState(pageCount = { verse.size })
    var pageNo by remember { mutableIntStateOf(0) }
    val openDialogCustom = remember { mutableStateOf(false) }
    val context = LocalContext.current

    BackHandler {
        navController.navigate(DrawerScreens.AllSongScreen.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(), verticalArrangement = Arrangement.Top
    ) {

        TopAppBar(
            title = {
                Text(
                    text = "Nyimbo Za Kristo",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigate(DrawerScreens.AllSongScreen.route) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Menu")
                }
            },
            backgroundColor = MaterialTheme.colors.primary,
            actions = {
                IconButton(onClick = { openDialogCustom.value = true }) {
                    Icon(Icons.Filled.FormatSize, "Font size")
                }
                IconButton(onClick = { viewModel.onLikeChecked(song = verse[pageNo]) }) {
                    when (verse[pageNo].like) {
                        true -> Icon(Icons.Rounded.Favorite, "Liked songs", tint = LikeColor)
                        false -> Icon(Icons.TwoTone.Favorite, "Unliked Song")
                    }
                }
                IconButton(onClick = { shareVerses(verse[pageNo], context) }) {
                    Icon(Icons.Filled.Share, "Share Icon")
                }
            }
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${verse[pageNo].songs_id}-${verse[pageNo].title}",
            textAlign = TextAlign.Center,
            color = LikeColor,
            style = MaterialTheme.typography.h1,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))

        // for opening dialog
        if (openDialogCustom.value) {
            FontSizeDialog(openDialogCustom = openDialogCustom, viewModel = viewModel)
        }


        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) { page ->
            val text =
                HtmlCompat.fromHtml(verse[page].verse_text, HtmlCompat.FROM_HTML_MODE_COMPACT)
            Text(
                text = text.toString(),
                fontSize = fontSize.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
        }

        LaunchedEffect(pagerState) {
            pagerState.scrollToPage(page)
            snapshotFlow { pagerState.currentPage }.collect { page ->
                pageNo = page
            }
        }
    }
}


fun shareVerses(songs: Songs, context: Context) {
    val text = HtmlCompat.fromHtml(songs.verse_text, HtmlCompat.FROM_HTML_MODE_COMPACT)
    val appText = "Pakua app yetu ya Nyimbo Za Kristo kwa Nyimbo zaidi. \r\n $APP_URL"

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            "${songs.songs_id}: ${songs.title}\r\n \r\n$text\r\n\r\n$appText"
        )
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}