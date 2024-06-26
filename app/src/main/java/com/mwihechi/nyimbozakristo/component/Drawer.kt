package com.mwihechi.nyimbozakristo.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Splitscreen
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

const val VERSE_ARGUMENT_KEY = "currentPage"
const val LOVE_ARGUMENT_KEY = "isLovedSongs"

sealed class DrawerScreens(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object AllSongScreen : DrawerScreens(
        route = "all_song_screen",
        title = "Nyimbo",
        icon = Icons.Rounded.Home
    )

    data object SplashScreen : DrawerScreens(
        route = "nyimbo_splash_screen",
        title = "Splash Screen",
        icon = Icons.Default.Splitscreen
    )

    data object LovedSong : DrawerScreens(
        route = "loved_song_screen",
        title = "Nyimbo Pendwa",
        icon = Icons.Rounded.Favorite
    )

    data object SettingScreen :
        DrawerScreens(route = "setting_screen", title = "Mipangilio", icon = Icons.Rounded.Settings)

    data object AboutScreen :
        DrawerScreens(route = "about_screen", title = "Kuhusu", icon = Icons.Rounded.Info)

    data object ShareScreen :
        DrawerScreens(route = "share_screen", title = "Sambaza app", icon = Icons.Rounded.Share)

    data object CategoryScreen :
        DrawerScreens(route = "category_screen", title = "Yaliyomo", icon = Icons.Rounded.Category)


    data object VerseScreen : DrawerScreens(
        route = "verse_screen/{$VERSE_ARGUMENT_KEY}/{$LOVE_ARGUMENT_KEY}",
        title = "Verses Screen",
        icon = Icons.Rounded.FavoriteBorder
    ) {
        fun passArgument(currentPage: Int, isLovedScreen: Boolean): String {
            return "verse_screen/$currentPage/$isLovedScreen"
        }
    }
}

private val screens = listOf(
    DrawerScreens.AllSongScreen,
    DrawerScreens.LovedSong,
    DrawerScreens.CategoryScreen,
    DrawerScreens.SettingScreen,
    DrawerScreens.AboutScreen,
    DrawerScreens.ShareScreen
)

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        screens.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable {
                        onDestinationClicked(item.route)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = item.icon,
                    tint = MaterialTheme.colors.onBackground,
                    contentDescription = item.title,
                    modifier = Modifier.weight(0.5f)
                )
                Text(text = item.title, modifier = Modifier.weight(2.0f))
            }
        }
    }
}
