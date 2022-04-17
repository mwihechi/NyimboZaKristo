package com.tansoften.nyimbozakristo.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.tansoften.nyimbozakristo.DrawerScreens
import com.tansoften.nyimbozakristo.R
import kotlinx.coroutines.delay


@Composable
fun NyimboSplashScreen(navController: NavHostController) {

    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.9f, animationSpec = tween(800, easing = {
                OvershootInterpolator(8f).getInterpolation(it)
            })
        )

        delay(3000L)
        navController.navigate(DrawerScreens.AllSongScreen.route)
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val surface = createRef()
        Surface(
            modifier = Modifier
                .padding(15.dp)
                .size(250.dp)
                .scale(scale.value)
                .constrainAs(surface) {
                    linkTo(parent.start, parent.end)
                    linkTo(parent.top, parent.bottom)
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
    }
}

@Preview
@Composable
fun NSSPreview() {
    //NyimboSplashScreen()
}
