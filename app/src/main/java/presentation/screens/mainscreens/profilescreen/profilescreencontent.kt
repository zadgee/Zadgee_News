package presentation.screens.mainscreens.profilescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.firebase.newsapp.R
import const.SIGN_OUT
import const.SIGN_UP
import domain.authorization.models.GmailAuthorizationUserData

@Composable
fun ProfileScreenContent(
    modifier:Modifier = Modifier,
    gmailAuthorizationUserData: GmailAuthorizationUserData?,
    name:String,
    onSignOutClick:()->Unit
){


    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {
        if (gmailAuthorizationUserData?.picture != null) {
            AsyncImage(
                    model = gmailAuthorizationUserData.picture,
                    contentDescription = "User Image",
                    modifier = modifier
                        .clip(CircleShape)
                        .size(120.dp)
                        .padding(16.dp)
                )

        } else{
            Image(
                painter = painterResource(id = R.drawable.baseline_person_24),
                contentDescription = "User Image, while authorizing with email/password",
                modifier = modifier.clip(CircleShape)
                    .size(120.dp)
            )
        }

        if (gmailAuthorizationUserData?.name != null) {
            Text(
                text = gmailAuthorizationUserData.name,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                fontSize = 15.sp,
            )
        } else {
            Text(
                text = name,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                fontSize = 24.sp,
            )
        }

        Button(
            onClick = onSignOutClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(
                text = SIGN_OUT,
                color = Color.Black
            )
        }
    }
}