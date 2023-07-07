package screens.screens
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.hilt.navigation.compose.hiltViewModel
import com.firebase.newsapp.R
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import domain.state.EmailVerificationState
import screens.viewmodels.AuthorizationViewModel


@SuppressLint("SuspiciousIndentation")
@Composable
fun EmailVerificationScreen(
    navigateToLoginScreen: () -> Unit
){
    val authorizationViewModel = hiltViewModel<AuthorizationViewModel>()
    val verificationState = authorizationViewModel.verificationEmailState.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)



        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { authorizationViewModel.isEmailVerifiedCorrectly()},
            modifier = Modifier.fillMaxSize(),
            indicatorAlignment = Alignment.Center
            ){
            Column{
                when(verificationState.value){
                    EmailVerificationState.NotVerified->{
                        Text(
                            text = "Please, verify your email",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                        )
                    }

                    EmailVerificationState.Loading->{
                        CircularProgressIndicator(
                            color = Color.White
                        )
                    }

                    EmailVerificationState.Verified->{
                        navigateToLoginScreen()
                        Log.d("TAG","User verify his own email")
                    }
                }
            }
        }
}