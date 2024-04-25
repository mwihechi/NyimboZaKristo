package com.mwihechi.nyimbozakristo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mwihechi.nyimbozakristo.viewModel.VersesViewModel
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
        // TODO add 8.pd elevation
    ) {
        Column(
            modifier.background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ukubwa wa maneno",
                style = MaterialTheme.typography.headlineSmall,
                modifier = modifier.padding(vertical = 5.dp)
            )

            //slider used to set font size
            if (fontSize != null) {
                val sliderPosition = remember { mutableFloatStateOf(fontSize) }
                val currentFontSize = (sliderPosition.floatValue * 100).roundToInt()
                Text(text = currentFontSize.toString(), style = MaterialTheme.typography.bodyLarge)
                Slider(
                    valueRange = 0.14f..0.30f,
                    value = sliderPosition.floatValue,
                    onValueChange = {
                        sliderPosition.floatValue = it
                    },
                    onValueChangeFinished = {
                        viewModel.updateFontSize(sliderPosition.floatValue)
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
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}