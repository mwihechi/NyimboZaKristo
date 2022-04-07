package com.tansoften.nyimbozakristo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.tansoften.nyimbozakristo.view_model.VersesViewModel
import kotlin.math.roundToInt

@Composable
fun FontSizeDialog(
    openDialogCustom: MutableState<Boolean>,
    viewModel: VersesViewModel
) {
    Dialog(onDismissRequest = { openDialogCustom.value = false }) {

        CustomDialogUI(
            openDialogCustom = openDialogCustom,
            viewModel = viewModel
        )
    }
}

@Composable
fun CustomDialogUI(
    modifier: Modifier = Modifier,
    openDialogCustom: MutableState<Boolean>,
    viewModel: VersesViewModel
) {
    val fontSize = viewModel.fontSize.observeAsState().value

    Card(
        shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier.background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ukubwa wa maneno",
                style = MaterialTheme.typography.h1,
                modifier = modifier.padding(vertical = 5.dp)
            )

            //slider used to set font size
            if (fontSize != null) {
                val sliderPosition = remember { mutableStateOf(fontSize) }
                val currentFontSize = (sliderPosition.value * 100).roundToInt()
                Text(text = currentFontSize.toString(), style = MaterialTheme.typography.body1)
                Slider(valueRange = 0.14f..0.30f, value = sliderPosition.value, onValueChange = {
                    sliderPosition.value = it
                }, onValueChangeFinished = {
                    viewModel.updateFontSize(sliderPosition.value)
                })
            }


            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.End
            ) {


                TextButton(onClick = { openDialogCustom.value = false }) {
                    Text(
                        "Badili",
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}