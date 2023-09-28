package presentation.screens.mainscreens.newsscreen.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.firebase.newsapp.R
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import domain.authorization.models.GmailAuthorizationUserData
import domain.models.NewsData
import presentation.newsapp.ui.theme.spacing.customDpOrSpSize
import presentation.viewmodels.NewsViewModel


@Composable
fun NewsScreenContent(
    modifier: Modifier = Modifier,
    gmailAuthorizationUserData: GmailAuthorizationUserData?,
    onProfileClick: () -> Unit,
    name:String,
    viewModel:NewsViewModel
) {
   val newsPagingItems : LazyPagingItems<NewsData> = viewModel.newsStateFlow.collectAsLazyPagingItems()
   val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    SwipeRefresh(state = swipeRefreshState, onRefresh = {

    }){
        Column{
            if (gmailAuthorizationUserData?.picture != null) {
                IconButton(onClick = onProfileClick) {
                    AsyncImage(
                        model = gmailAuthorizationUserData.picture,
                        contentDescription = "User Image",
                        modifier = modifier
                            .clip(CircleShape)
                            .size(30.dp)
                    )
                }
            } else{
                IconButton(onClick = onProfileClick) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_person_24),
                        contentDescription = "User Image, while authorizing with email/password"
                    )
                }
            }

            if (gmailAuthorizationUserData?.name != null) {
                Text(
                    text = gmailAuthorizationUserData.name,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                    fontSize = 20.sp
                )
            } else {
                Text(
                    text = name,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                    fontSize = 17.sp
                )
            }

            Spacer(modifier = modifier.padding(customDpOrSpSize.current.medium))

            LazyColumn{
                items(newsPagingItems.itemCount){ index->
                    NewsCard(
                        newsData = newsPagingItems[index]
                    )
                }
            }
        }
    }
    }