package com.tansoften.nyimbozakristo.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tansoften.nyimbozakristo.R



val QuickSand = FontFamily(
    Font(resId = R.font.quicksand_regular),
    Font(resId = R.font.quicksand_bold, FontWeight.Bold)
)

val Roboto = FontFamily(
    Font(resId = R.font.roboto_regular),
    Font(resId = R.font.roboto_bold, FontWeight.Bold)
)

val Poppins = FontFamily(
    Font(resId = R.font.poppins_regular),
    Font(resId = R.font.poppins_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),

    h1 = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),



    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)