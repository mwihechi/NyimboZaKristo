package com.tansoften.nyimbozakristo.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tansoften.nyimbozakristo.view_model.SettingViewModel
import kotlin.math.roundToInt

@Composable
fun SettingScreen(viewModel: SettingViewModel = hiltViewModel(), navController: NavController) {
    val fontSize = viewModel.fontSize.observeAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            if (fontSize != null) {
                val sliderPosition = remember { mutableStateOf(fontSize) }
                val currentFontSize = (sliderPosition.value * 100).roundToInt()

                val (textTitle, textFontSize, iconFormatSize, slider) = createRefs()

                Text(
                    text = "Badili ukubwa wa maandishi",
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.constrainAs(textTitle) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    })
                Icon(
                    imageVector = Icons.Filled.FormatSize,
                    contentDescription = "Image format icon",
                    modifier = Modifier.constrainAs(iconFormatSize){
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                )
                Text(text = currentFontSize.toString())

                Slider(valueRange = 0.14f..0.30f, value = sliderPosition.value, onValueChange = {
                    sliderPosition.value = it
                }, onValueChangeFinished = {
                    viewModel.updateFontSize(sliderPosition.value)
                })
            }
        }


    }
}
