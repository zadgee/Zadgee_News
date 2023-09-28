package presentation.views
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingEvent(){
       CircularProgressIndicator(
            color = Color.White
        )
}

