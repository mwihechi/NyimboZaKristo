package com.tansoften.nyimbozakristo.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tansoften.nyimbozakristo.DrawerScreens
import com.tansoften.nyimbozakristo.storage.SortOrder
import com.tansoften.nyimbozakristo.view_model.SettingViewModel
import kotlin.math.roundToInt

@Composable
fun SettingScreen(viewModel: SettingViewModel = hiltViewModel(), navController: NavController) {
    BackHandler {
        navController.navigate(DrawerScreens.AllSongScreen.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        DefaultAppBar(
            title = "Mipangilio",
            buttonIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onButtonClicked = { navController.popBackStack() })

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
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(100.dp)
    ) {
        ConstraintLayout {
            val (textTitle, textFontSize, slider, textDesc) = createRefs()

            Text(
                text = "Ukubwa wa maneno",
                fontSize = 15.sp,
                modifier = Modifier.constrainAs(textTitle) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(parent.top)
                }
            )

            Text(
                text = "Ongeza au punguza ukubwa wa maneno kadili ya mahitaji yako.",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.constrainAs(textDesc) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(textTitle.bottom)
                    width = Dimension.preferredWrapContent
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
                        top.linkTo(textDesc.bottom)
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
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(100.dp)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (textTitle, textExplanation, switch) = createRefs()
            val isScreenOn = viewModel.isScreenOn.observeAsState().value

            val text = "Wezesha mwanga wa simu kutozima pindi unapukuwa ukisoma nyimbo."

            Text(
                text = "Wezesha mwanga kutozima",
                fontSize = 15.sp,
                modifier = Modifier.constrainAs(textTitle) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(parent.top)
                }
            )

            Text(
                text = text,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Start,
                modifier = Modifier.constrainAs(textExplanation) {
                    linkTo(parent.start, switch.start, startMargin = 8.dp)
                    top.linkTo(textTitle.bottom)
                    width = Dimension.preferredWrapContent
                }
            )


            if (isScreenOn != null) {
                var checkedState by remember { mutableStateOf(isScreenOn) }
                Switch(checked = checkedState, onCheckedChange = {
                    checkedState = it
                    viewModel.updateIsScreenOn(isScreenOn = it)
                }, modifier = Modifier.constrainAs(switch) {
                    linkTo(parent.top, textExplanation.bottom)
                    end.linkTo(parent.end)
                })
            }

            // createHorizontalChain(textExplanation, switch,  chainStyle = ChainStyle.SpreadInside)
        }
    }
}

@Composable
fun SortOrderCard(viewModel: SettingViewModel) {
    Card(
        shape = RoundedCornerShape(4.dp),
        backgroundColor = MaterialTheme.colors.background,
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
                fontSize = 15.sp,
                modifier = Modifier.constrainAs(textTitle) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(parent.top)
                }
            )

            Text(
                text = text,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.constrainAs(textExplanation) {
                    linkTo(parent.start, switch.start, startMargin = 8.dp)
                    top.linkTo(textTitle.bottom)
                    width = Dimension.fillToConstraints
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
                        when (sortOrder.sortOrder) {
                            SortOrder.BY_NAME -> SortOrder.BY_NUMBER
                            SortOrder.BY_NUMBER -> SortOrder.BY_NAME
                        }
                    )
                }, modifier = Modifier.constrainAs(switch) {
                    linkTo(parent.top, textExplanation.bottom)
                    end.linkTo(parent.end)
                })
            }
        }
    }
}