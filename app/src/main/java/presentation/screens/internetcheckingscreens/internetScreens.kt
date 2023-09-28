package presentation.screens.internetcheckingscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.firebase.newsapp.R
import const.CORRECTION_ERROR
import const.WAITING_TEXT

@Composable
fun BadInternet(){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color =  Color.White
        )
        Text(text = CORRECTION_ERROR,
            color = Color.White,
            modifier = Modifier.padding(15.dp),
            fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
        )
    }
}

@Composable
fun NoInternetScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(color = Color.White)
        Text(text = WAITING_TEXT,
            fontFamily = FontFamily(
                Font(
                    R.font.maintextfontinapp,
                )
            ),
            color = Color.White,
            modifier = Modifier.padding(5.dp)
        )}}

