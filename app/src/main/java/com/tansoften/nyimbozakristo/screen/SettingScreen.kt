package com.tansoften.nyimbozakristo.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tansoften.nyimbozakristo.view_model.SettingViewModel
import kotlin.math.roundToInt

@Composable
fun SettingScreen(viewModel: SettingViewModel = hiltViewModel(), navController: NavController) {
    val fontSize = viewModel.fontSize.observeAsState().value


    Column(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(
            modifier = Modifier
                .requiredHeight(50.dp)
                .fillMaxSize()
        ) {

            val (arrowIcon, titleText) = createRefs()

            IconButton(onClick = {
                navController.popBackStack()
            }, modifier = Modifier.constrainAs(arrowIcon) {
                centerVerticallyTo(parent)
                start.linkTo(parent.start)
            }) {
                Icon(Icons.Filled.ArrowBack, "Menu")
            }
            Text(
                text = "Mipangilio",
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(titleText) {
                    centerVerticallyTo(parent)
                    start.linkTo(arrowIcon.end)
                    end.linkTo(parent.end)
                },
                style = MaterialTheme.typography.h1
            )
        }

        Card(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.padding(4.dp)
        ) {
            ConstraintLayout {
                val (textTitle, textFontSize, slider) = createRefs()

                Text(
                    text = "Ukubwa wa maneno",
                    modifier = Modifier.constrainAs(textTitle){
                        start.linkTo(parent.start, margin = 8.dp)
                        top.linkTo(parent.top)
                    }
                )

                //slider used to set font size
                if (fontSize != null) {
                    val sliderPosition = remember { mutableStateOf(fontSize) }
                    val currentFontSize = (sliderPosition.value * 100).roundToInt()
                    Text(text = currentFontSize.toString(), modifier = Modifier.constrainAs(textFontSize){
                        end.linkTo(parent.end, margin = 8.dp)
                        top.linkTo(parent.top)

                    })
                    Slider(
                        valueRange = 0.14f..0.30f,
                        value = sliderPosition.value,
                        onValueChange = {
                            sliderPosition.value = it
                        },
                        onValueChangeFinished = {
                            viewModel.updateFontSize(sliderPosition.value)
                        },
                        modifier = Modifier.constrainAs(slider){
                            top.linkTo(textTitle.bottom)
                            centerHorizontallyTo(parent)
                        }
                    )
                }

            }
        }

    }
}
