package com.mwihechi.nyimbozakristo.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.mwihechi.nyimbozakristo.ui.theme.md_theme_light_primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppAppBar(
    title: String = "",
    buttonIcon: ImageVector,
    onBackButtonClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackButtonClick() }) {
                Icon(buttonIcon, contentDescription = "")
            }
        },
        colors = TopAppBarColors(
            containerColor = md_theme_light_primary,
            scrolledContainerColor = md_theme_light_primary,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}