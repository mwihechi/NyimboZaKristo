package com.tansoften.nyimbozakristo.screen

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.tansoften.nyimbozakristo.APP_URL


@Composable
fun ShareApp() {
    // navController.navigate(DrawerScreens.AllSongScreen.route)
    val context = LocalContext.current
    val appText =
        "Soma Nyimbo Za Kristo kirahisi kabisa, muonekano mzuri na uwezo wa kupangilia vitu kama utakavyo wewe. Ipakue sasa kupitia link hii hapo chini. \r\n $APP_URL"
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            appText
        )
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}