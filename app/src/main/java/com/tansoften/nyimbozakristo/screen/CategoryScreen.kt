package com.tansoften.nyimbozakristo.screen

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController


@Composable
fun CategoryScreen(navController: NavHostController) {
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
                text = "Yaliyomo",
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(titleText) {
                    centerVerticallyTo(parent)
                    start.linkTo(arrowIcon.end)
                    end.linkTo(parent.end)
                },
                style = MaterialTheme.typography.h1
            )
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Kipengele hiki bado kipo kwenye matengenezo",
                textAlign = TextAlign.Center
            )
        }

    }
}