package com.example.movies.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val normal = TextStyle(
    fontWeight = FontWeight.Normal
)

val bold = TextStyle(
    fontWeight = FontWeight.Bold
)

val normalSize14 = normal.copy(
    fontSize = 14.sp
)

val normalSize14LightGray = normalSize14.copy(
    color = LightGray
)

val normalSize16 = normal.copy(
    fontSize = 16.sp
)

val normalSize16VeryDarkGray = normalSize16.copy(
    color = veryDarkGray
)

val normalSize16White = normalSize16.copy(
    color = white
)

val normalSize18 = normal.copy(
    fontSize = 18.sp
)

val normalSize18White = normalSize18.copy(
    color = white
)

val normalSize20 = normal.copy(
    fontSize = 20.sp
)

val normalSize20White = normalSize20.copy(
    color = white
)

val boldSize16 = bold.copy(
    fontSize = 16.sp
)

val boldSize16VeryDarkGray = boldSize16.copy(
    color = veryDarkGray
)

val boldSize20 = bold.copy(
    fontSize = 20.sp
)

val boldSize20White = boldSize20.copy(
    color = white
)
