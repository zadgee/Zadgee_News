package presentation.newsapp.ui.theme.spacing

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DpOrSpSize(
    val extraSmall:Dp = 4.dp,
    val small: Dp = 8.dp,
    val biggerThanSmall : Dp = 15.dp,
    val medium : Dp = 20.dp,
    val extraMedium : Dp = 35.dp,
    val spaceBetweenAuthorAndDateTexts:Dp = 270.dp,
    val huge : Dp = 50.dp,
    val extraHuge: Dp = 80.dp,
    val newsTitleSp: TextUnit = 30.sp,
    val newsCardParamSize:TextUnit = 20.sp
)
val customDpOrSpSize  = compositionLocalOf { DpOrSpSize() }
