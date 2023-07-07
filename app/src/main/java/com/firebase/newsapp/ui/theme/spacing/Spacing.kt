package com.firebase.newsapp.ui.theme.spacing

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val small: Dp = 8.dp,
    val medium : Dp = 20.dp,
    val extraMedium : Dp = 35.dp,
    val huge : Dp = 50.dp
)
val customSpacing  = compositionLocalOf { Spacing() }