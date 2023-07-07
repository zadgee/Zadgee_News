package navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import authorization.mappers.toGmailUser
import authorization.mappers.toUser
import com.google.firebase.auth.GoogleAuthProvider
import domain.state.EmailVerificationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import screens.screens.LoginScreen
import screens.internetcheckingscreens.BadInternet
import screens.internetcheckingscreens.NoInternetScreen
import screens.screens.EmailVerificationScreen
import screens.screens.InternetScreenRoutes
import screens.screens.NewsScreen
import screens.screens.ProfileScreen
import screens.screens.ScreensRoute
import screens.screens.SignUpScreen
import screens.viewmodels.AuthorizationViewModel
import screens.viewmodels.NewsViewModel
import java.util.concurrent.CancellationException

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CustomNavigationInApp() {
    val navigationScope = CoroutineScope(Dispatchers.Main)
    val navController = rememberNavController()
    val authorizationViewModel = hiltViewModel<AuthorizationViewModel>()
    val newsViewModel = hiltViewModel<NewsViewModel>()


    val launcherGmailSignIn = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ){
            activityResult ->
        if(activityResult.resultCode == Activity.RESULT_OK){
            try {
                val credentials = authorizationViewModel.oneTapClient.getSignInCredentialFromIntent(
                    activityResult.data
                )
                val googleIdToken = credentials.googleIdToken
                val googleCred = GoogleAuthProvider.getCredential(googleIdToken,null)
                authorizationViewModel.gmailAuth(googleCred)
            }catch (e:Exception){
                e.printStackTrace()
                if(e is CancellationException){
                    Log.e("TAG","${e.message}")
                }
            }
        }
    }


    NavHost(navController = navController, startDestination = ScreensRoute.SignUp.route) {
            composable(ScreensRoute.SignUp.route) {
            LaunchedEffect(key1 = Unit){
                val isUserAuthorizedWithGoogleSignIn = authorizationViewModel.authorizedUserWithGmail()
                val isUserAuthorizedWithEmailPassword = authorizationViewModel.getSignInUser()
                if(isUserAuthorizedWithGoogleSignIn != null || isUserAuthorizedWithEmailPassword){
                    navController.navigate(ScreensRoute.News.route)
                }
            }


            SignUpScreen(navigateToLogin = { navController.navigate(ScreensRoute.SignIn.route) },
               navigateFromGoogleSignUp = {
                    navigationScope.launch{
                        launcherGmailSignIn.launch(
                            IntentSenderRequest.Builder(
                                authorizationViewModel.gmailSignUp()
                            ).build()
                        )
                        navController.navigate(ScreensRoute.News.route)
                    }
                },
                navigateToVerifyEmail = {
                    navigationScope.launch {
                         navController.navigate(ScreensRoute.EmailVerification.route)
                    }
                    }
            )

        }

composable(ScreensRoute.EmailVerification.route){
    EmailVerificationScreen(
        navigateToLoginScreen = { navController.navigate(ScreensRoute.SignIn.route) }
    )
}



        composable(ScreensRoute.SignIn.route) {

            LoginScreen(
                navigateToNewsScreen = {
                        navController.navigate(ScreensRoute.News.route)

                }, signInWithGoogle = {
                    navigationScope.launch{
                        launcherGmailSignIn.launch(
                            IntentSenderRequest.Builder(
                                authorizationViewModel.gmailSignIn()
                            ).build()
                        )
                        navController.navigate(ScreensRoute.News.route)
                    }
                }
            )

        }


        composable(
            ScreensRoute.News.route
        ) {

            NewsScreen(
                onProfileClick = {
                    navController.navigate(ScreensRoute.Profile.route)
                },
                gmailAuthorizationUserData = authorizationViewModel.firebaseAuth.currentUser?.toGmailUser(),
                newsDisplayingModelDTO = newsViewModel.newsState.value.newsDisplay,
                defaultAuthorizationData = authorizationViewModel.firebaseAuth.currentUser?.toUser()
            )

        }

        composable(InternetScreenRoutes.NoInternetScreen.route) {
            NoInternetScreen()
        }

        composable(InternetScreenRoutes.BadInternetScreen.route) {
            BadInternet()
        }

        composable(ScreensRoute.Profile.route){
            ProfileScreen(signOutClick = {
                navController.navigate(ScreensRoute.SignIn.route)
            }, backToNewsScreen = {
                navController.popBackStack()
            })
        }

    }

}