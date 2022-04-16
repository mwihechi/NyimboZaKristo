package com.tansoften.nyimbozakristo.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tansoften.nyimbozakristo.DrawerScreens
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

    Surface(
        modifier = Modifier
            .padding(15.dp)
            .height(25.dp)
            .width(150.dp)
            .scale(scale.value),
        shape = RectangleShape,
        color = MaterialTheme.colors.background,
        border =  BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Text(text = "Splash Screen")
    }
}

@Preview
@Composable
fun NSSPreview() {
    //NyimboSplashScreen()
}