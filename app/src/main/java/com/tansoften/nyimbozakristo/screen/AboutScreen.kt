package com.tansoften.nyimbozakristo.screen

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.tansoften.nyimbozakristo.BuildConfig
import com.tansoften.nyimbozakristo.DrawerScreens


@Composable
fun AboutScreen(navController: NavController) {
    val context = LocalContext.current
    val manager = context.packageManager.getPackageInfo(context.packageName, 0)

    Column(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(
            modifier = Modifier
                .requiredHeight(50.dp)
                .fillMaxSize()
        ) {

            val (arrowIcon, titleText) = createRefs()

            IconButton(onClick = {
                navController.navigate(DrawerScreens.AllSongScreen.route)
            }, modifier = Modifier.constrainAs(arrowIcon) {
                centerVerticallyTo(parent)
                start.linkTo(parent.start)
            }) {
                Icon(Icons.Filled.ArrowBack, "Menu")
            }
            Text(
                text = "Kuhusu",
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(titleText) {
                    centerVerticallyTo(parent)
                    start.linkTo(arrowIcon.end)
                    end.linkTo(parent.end)
                },
                style = MaterialTheme.typography.h1
            )
        }


        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
           // Text(text = "Nyimbo Za Kristo.")
            Text(text = "Toleo Namba: ${manager.versionName}")
        }
    }

}

