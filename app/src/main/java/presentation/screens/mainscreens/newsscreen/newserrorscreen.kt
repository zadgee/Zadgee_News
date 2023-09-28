package presentation.screens.mainscreens.newsscreen
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun NewsErrorScreen(
    error:String?,
    goBackToNewsScreen:()->Unit
){

    error?.let {
        Box(contentAlignment = Alignment.Center){
            Text(
                text = it,
                color = Color.Red
            )
        }
    }?:goBackToNewsScreen()
}