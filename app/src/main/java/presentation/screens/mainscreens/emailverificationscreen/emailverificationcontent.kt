package presentation.screens.mainscreens.emailverificationscreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.firebase.newsapp.R
import presentation.newsapp.ui.theme.spacing.customDpOrSpSize
import const.AD_ID
import domain.state.ReloadingUserResult
import presentation.views.AdBanner
import presentation.views.LoadingEvent


@Composable
fun EmailVerificationScreenContent(
    modifier:Modifier=Modifier,
    reloadState:ReloadingUserResult,
    onAlreadyVerifiedClick:() -> Unit,
    ){




    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Please, verify your email",
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.maintextfontinapp))
        )

        Spacer(modifier = modifier.padding(customDpOrSpSize.current.extraMedium))


        if(reloadState.isLoading){
            LoadingEvent()
        }

        Text(
            text = "Already verified?",
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
            modifier = modifier.clickable {
                onAlreadyVerifiedClick()
            }
        )

        Spacer(modifier = modifier.padding(customDpOrSpSize.current.medium))


        AdBanner(id = AD_ID)

    }

}