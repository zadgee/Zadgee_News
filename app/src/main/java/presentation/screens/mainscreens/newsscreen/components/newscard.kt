package presentation.screens.mainscreens.newsscreen.components
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.firebase.newsapp.R
import const.REQUESTED_TIME_PATTERN
import domain.models.NewsData
import presentation.newsapp.ui.theme.customcolors.MyColors
import presentation.newsapp.ui.theme.spacing.customDpOrSpSize
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun NewsCard(
    newsData: NewsData?,
    modifier:Modifier=Modifier,
){



    Card(
       modifier.fillMaxWidth(),
       shape = RoundedCornerShape(customDpOrSpSize.current.biggerThanSmall),
       colors = CardDefaults.cardColors(
           containerColor = MyColors.Pink,
           contentColor = MyColors.Pink
       )
   ){

       Spacer(modifier = modifier.padding(customDpOrSpSize.current.small))



       Text(
           text = newsData?.title.toString(),
           fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
           color = Color.White,
           modifier = modifier.align(CenterHorizontally),
           fontSize = customDpOrSpSize.current.newsTitleSp
       )


       modifier.padding(customDpOrSpSize.current.small)

           AsyncImage(model = newsData?.pic,
               contentDescription = "Google Icon"
           )


       modifier.padding(customDpOrSpSize.current.extraHuge)

       Box{
           Row{
               Text(
                   text = parsedDate(newsData?.newsDate),
                   fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                   color = Color.White,
                   fontSize = customDpOrSpSize.current.newsCardParamSize,
                   textAlign = TextAlign.Start
               )


               Text(
                   text = newsData?.author.toString(),
                   modifier = modifier.padding(
                       start = customDpOrSpSize.current.spaceBetweenAuthorAndDateTexts
                   ),
                   fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                   color = Color.White,
                   fontSize = customDpOrSpSize.current.newsCardParamSize,
                   textAlign = TextAlign.End
               )
           }
       }



   }  
}

fun parsedDate(newsDate: String?): String {
    val inputFormat = SimpleDateFormat(REQUESTED_TIME_PATTERN, Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = inputFormat.parse(newsDate.toString())
    return date?.let { outputFormat.format(it) }.toString()
}
