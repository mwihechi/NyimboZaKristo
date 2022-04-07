package com.tansoften.nyimbozakristo.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tansoften.nyimbozakristo.storage.SortOrder
import com.tansoften.nyimbozakristo.view_model.SettingViewModel
import kotlin.math.roundToInt

@Composable
fun SettingScreen(viewModel: SettingViewModel = hiltViewModel(), navController: NavController) {

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

        // Font size card
        FontSizeCard(viewModel = viewModel)

        // Screen stay awake card
        KeepScreenOnCard(viewModel = viewModel)

        // Sort order card
        SortOrderCard(viewModel = viewModel)
    }
}


@Composable
fun FontSizeCard(viewModel: SettingViewModel) {
    val fontSize = viewModel.fontSize.observeAsState().value
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        ConstraintLayout {
            val (textTitle, textFontSize, slider) = createRefs()

            Text(
                text = "Ukubwa wa maneno",
                fontWeight = FontWeight.Black,
                modifier = Modifier.constrainAs(textTitle) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(parent.top)
                }
            )

            //slider used to set font size
            if (fontSize != null) {
                val sliderPosition = remember { mutableStateOf(fontSize) }
                val currentFontSize = (sliderPosition.value * 100).roundToInt()
                Text(
                    text = currentFontSize.toString(),
                    modifier = Modifier.constrainAs(textFontSize) {
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
                    modifier = Modifier.constrainAs(slider) {
                        top.linkTo(textTitle.bottom)
                        centerHorizontallyTo(parent)
                    }
                )
            }
        }
    }
}


@Composable
fun KeepScreenOnCard(viewModel: SettingViewModel) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        ConstraintLayout {
            val (textTitle, textExplanation, switch) = createRefs()
            val isScreenOn = viewModel.isScreenOn.observeAsState().value

            val text = "Wezesha mwanga wa simu kutozima pindi unapukuwa ukisoma"

            Text(
                text = "Wezesha mwanga kutozima",
                fontWeight = FontWeight.Black,
                modifier = Modifier.constrainAs(textTitle) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(parent.top)
                }
            )

            Text(
                text = text,
                modifier = Modifier.constrainAs(textExplanation) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(textTitle.bottom)
                }
            )


            if (isScreenOn != null) {
                var checkedState by remember { mutableStateOf(isScreenOn) }
                Switch(checked = checkedState, onCheckedChange = {
                    checkedState = it
                    viewModel.updateIsScreenOn(isScreenOn = it)
                }, modifier = Modifier.constrainAs(switch) {
                    end.linkTo(parent.end, margin = 8.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(textExplanation.bottom)
                })
            }

        }
    }
}

@Composable
fun SortOrderCard(viewModel: SettingViewModel) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        ConstraintLayout {
            val (textTitle, textExplanation, switch) = createRefs()
            val sortOrder = viewModel.sortOrder.observeAsState().value

            val text = "Pangilia orodha ya Nyimbo kwa kufuata alfabeti ya jina la wimbo"

            Text(
                text = "Aina ya mpangilio",
                fontWeight = FontWeight.Black,
                modifier = Modifier.constrainAs(textTitle) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(parent.top)
                }
            )

            Text(
                text = text,
                modifier = Modifier.constrainAs(textExplanation) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(textTitle.bottom)
                }
            )

            if (sortOrder != null) {
                //  var checkedState by remember { mutableStateOf(isScreenOn) }

                var checkedState by remember {
                    when (sortOrder.sortOrder) {
                        SortOrder.BY_NAME -> mutableStateOf(true)
                        SortOrder.BY_NUMBER -> mutableStateOf(false)
                    }
                }

                Switch(checked = checkedState, onCheckedChange = {
                    checkedState = it
                    viewModel.updateSortOrder(
                        when(sortOrder.sortOrder){
                            SortOrder.BY_NAME -> SortOrder.BY_NUMBER
                            SortOrder.BY_NUMBER -> SortOrder.BY_NAME
                        }
                    )
                }, modifier = Modifier.constrainAs(switch) {
                    end.linkTo(parent.end, margin = 8.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(textExplanation.bottom)
                })
            }
        }
    }
}