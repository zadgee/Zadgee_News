package presentation.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import presentation.newsapp.ui.theme.NewsAppTheme
import presentation.newsapp.ui.theme.customcolors.MyColors
import dagger.hilt.android.AndroidEntryPoint
import presentation.CustomNavigationInApp
import presentation.screens.internetcheckingscreens.BadInternet
import presentation.screens.internetcheckingscreens.ConnectivityState
import presentation.screens.internetcheckingscreens.NetworkConnectivityChecking
import presentation.screens.internetcheckingscreens.NoInternetScreen


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private  lateinit var connectivityObserver: ConnectivityState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MyColors.bluefigmacolor
                ) {
                    val context = LocalContext.current
                    connectivityObserver  =
                        NetworkConnectivityChecking(
                            context
                        )
                    val status  by connectivityObserver.observeConnection().collectAsState(initial = context.applicationContext)
                    when (status) {
                        ConnectivityState.Status.Unavailable -> NoInternetScreen()
                        ConnectivityState.Status.Losing -> BadInternet()
                        ConnectivityState.Status.Lost -> NoInternetScreen()
                        ConnectivityState.Status.Available -> CustomNavigationInApp()
                    }
                }
            }
        }
    }



}