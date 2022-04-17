package com.tansoften.nyimbozakristo.screen

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.tansoften.nyimbozakristo.ui.theme.LikeColor

@Composable
fun DefaultAppBar(title: String = "", buttonIcon: ImageVector, onButtonClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
                Icon(buttonIcon, "description")
            }
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

