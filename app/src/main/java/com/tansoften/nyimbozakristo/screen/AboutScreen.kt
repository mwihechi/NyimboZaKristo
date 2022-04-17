package com.tansoften.nyimbozakristo.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tansoften.nyimbozakristo.DrawerScreens
import com.tansoften.nyimbozakristo.R


@Composable
fun AboutScreen(navController: NavController) {
    val context = LocalContext.current
    val manager = context.packageManager.getPackageInfo(context.packageName, 0)

    BackHandler {
        navController.navigate(DrawerScreens.AllSongScreen.route)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        DefaultAppBar(
            title = "Kuhusu",
            buttonIcon = Icons.Filled.ArrowBack,
            onButtonClicked = { navController.navigate(DrawerScreens.AllSongScreen.route) })

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (imageVector, textVersion, textAppName, textDeveloperInfo) = createRefs()
            val topGuideLine = createGuidelineFromTop(0.25f)

            Surface(
                modifier = Modifier
                    .padding(15.dp)
                    .size(250.dp)
                    .constrainAs(imageVector) {
                        linkTo(parent.start, parent.end)
                        top.linkTo(topGuideLine)
                    },
                shape = CircleShape,
                color = MaterialTheme.colors.background,
                border = BorderStroke(width = 2.dp, color = Color.LightGray)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.splash),
                    contentDescription = null
                )
            }


            Text(text = "Nyimbo Za Kristo",
                modifier = Modifier.constrainAs(textAppName) {
                    top.linkTo(imageVector.bottom, margin = 8.dp)
                    linkTo(parent.start, parent.end)
                    width = Dimension.preferredWrapContent
                })


            Text(
                text = "Toleo Namba: ${manager.versionName}",
                modifier = Modifier.constrainAs(textVersion) {
                    top.linkTo(textAppName.bottom, margin = 8.dp)
                    linkTo(parent.start, parent.end)
                    width = Dimension.preferredWrapContent
                })

            Text(text = "Imeundwa na \"Zacharia Mwihechi\"",
                modifier = Modifier.constrainAs(textDeveloperInfo) {
                    linkTo(parent.start, parent.end)
                    bottom.linkTo(parent.bottom)
                })
        }
    }

}

//Text(text = "Toleo Namba: ${manager.versionName}")

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen(navController = rememberNavController())
}
