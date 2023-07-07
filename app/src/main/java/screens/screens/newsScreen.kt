package screens.screens
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import authorization.models.DefaultAuthorizationData
import authorization.models.GmailAuthorizationUserData
import coil.compose.AsyncImage
import com.firebase.newsapp.R
import com.firebase.newsapp.ui.theme.customcolors.MyColors
import com.firebase.newsapp.ui.theme.spacing.customSpacing
import const.NEWS_DISPLAYING_ERROR
import const.SOMETHING_WENT_WRONG_ERROR
import const.SOMETHING_WENT_WRONG_IN_DATA
import remote.api.NewsDataDTO
import remote.api.NewsDisplayingModelDTO
import screens.viewmodels.NewsViewModel





@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NewsScreen(
    onProfileClick:() ->Unit,
    gmailAuthorizationUserData: GmailAuthorizationUserData?,
    modifier: Modifier = Modifier,
    newsDisplayingModelDTO: NewsDisplayingModelDTO?,
    defaultAuthorizationData: DefaultAuthorizationData?
){
    val viewModel = hiltViewModel<NewsViewModel>()
    val state = viewModel.newsState.collectAsState().value





    Column {
        if (gmailAuthorizationUserData?.picture != null) {
            IconButton(onClick = onProfileClick) {
                AsyncImage(
                    model = gmailAuthorizationUserData.picture, contentDescription = "User Image",
                    alignment = Alignment.CenterStart,
                    modifier = modifier
                        .clip(CircleShape)
                        .size(30.dp),
                )
            }
        } else {
            IconButton(onClick = onProfileClick) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = "Default User Image", modifier.align(Alignment.End),
                    tint = Color.White
                )
            }
        }


        if (gmailAuthorizationUserData?.name != null) {
            Text(
                text = gmailAuthorizationUserData.name,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                fontSize = 10.sp
            )
        } else {
            Text(
                text = "${defaultAuthorizationData?.name}",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.maintextfontinapp))
            )
        }

        LaunchedEffect(key1 = Unit){
            viewModel.displayNews()
        }

        LazyColumn(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            if (state.isLoading) {
                item {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.wrapContentSize(align = Center)
                    )
                }
            } else {
                items(newsDisplayingModelDTO?.newsComponents?.size ?: 0) { index ->
                    val newsDataDTO = newsDisplayingModelDTO?.newsComponents?.get(index)
                    NewsCards(
                        newsDataDTO = newsDataDTO
                    )
                }
                if (state.error != null) {
                    item {
                        Text(
                            text = NEWS_DISPLAYING_ERROR,
                            color = MyColors.Red,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.maintextfontinapp))
                        )
                    }
                } else {
                    item{
                        Text(
                            text = SOMETHING_WENT_WRONG_ERROR,
                            color = Color.Red,
                            fontFamily = FontFamily(Font(R.font.maintextfontinapp))
                        )
                    }
                }


            }
        }
    }
}


@Composable
fun NewsCards(newsDataDTO: NewsDataDTO?, modifier: Modifier = Modifier) {
    if(newsDataDTO != null){
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MyColors.Pink
            ),
            shape = RoundedCornerShape(7.dp)
        ) {
            AsyncImage(
                model = newsDataDTO.pic,
                contentDescription = "News Image"
            )
            Text(
                text = newsDataDTO.author,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.maintextfontinapp))
            )

            Spacer(modifier.padding(customSpacing.current.small))

            Text(
                text = newsDataDTO.newsDate.toLocalizedPattern().toString(),
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.maintextfontinapp))
            )
        }
    }else{
        Text(
            text = SOMETHING_WENT_WRONG_IN_DATA,
            color = Color.Red,
            fontFamily = FontFamily(Font(R.font.maintextfontinapp))
        )
    }
}