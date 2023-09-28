package presentation 
import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.screens.internetcheckingscreens.BadInternet
import presentation.screens.internetcheckingscreens.NoInternetScreen
import presentation.screens.loadingscreen.LoadingScreen
import presentation.screens.mainscreens.emailverificationscreen.EmailVerificationScreen
import presentation.screens.mainscreens.loginscreen.LoginScreen
import presentation.screens.mainscreens.newsscreen.NewsErrorScreen
import presentation.screens.mainscreens.newsscreen.NewsScreen
import presentation.screens.mainscreens.profilescreen.ProfileScreen
import presentation.screens.mainscreens.screensroute.InternetScreenRoutes
import presentation.screens.mainscreens.screensroute.ScreensRoute
import presentation.screens.mainscreens.signupscreen.SignUpScreen
import presentation.viewmodels.AuthorizationViewModel
import presentation.viewmodels.DataStoreViewModel
import presentation.viewmodels.LocalDataBaseViewModel
import presentation.viewmodels.ProfileViewModel
import java.util.concurrent.CancellationException

@SuppressLint("StateFlowValueCalledInComposition", "SuspiciousIndentation")
@Composable
fun CustomNavigationInApp() {
    val navigationScope = CoroutineScope(Dispatchers.Main)
    val navController = rememberNavController()
    val authorizationViewModel = hiltViewModel<AuthorizationViewModel>()
    val dataStoreViewModel = hiltViewModel<DataStoreViewModel>()
    val localDataBaseViewModel = hiltViewModel<LocalDataBaseViewModel>()
    val signUpState = authorizationViewModel.signUpState.collectAsState()
    val gmailAuthState = authorizationViewModel.gmailStateAuthorization.collectAsState()
    val context = LocalContext.current.applicationContext
    val profileViewModel = hiltViewModel<ProfileViewModel>()
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


    NavHost(navController = navController, startDestination = ScreensRoute.Loading.route) {

        composable(ScreensRoute.Loading.route){
            LoadingScreen()
            LaunchedEffect(key1 = Unit){
                delay(1000)
                val isUserAuthorizedWithGoogleSignIn =
                    authorizationViewModel.authorizedUserWithGmail()
                val isUserSignedUpWithEmailPassword = dataStoreViewModel.getSignedUpUser()
                val authorizedUserEmail = dataStoreViewModel.getAlreadySignedInUserEmail()
                val isUserSignedInWithEmailPassword =
                    localDataBaseViewModel.getSignInUser(authorizedUserEmail.toString())
                if(isUserAuthorizedWithGoogleSignIn != null ||
                    isUserSignedInWithEmailPassword != null){
                    dataStoreViewModel.saveVerifiedUserToDataStore(false)
                    navController.navigate(ScreensRoute.News.route)
                }else if(isUserSignedUpWithEmailPassword != null){
                    navController.navigate(ScreensRoute.SignIn.route)
                }else{
                    navController.navigate(ScreensRoute.SignUp.route)
                }
            }
        }

            composable(ScreensRoute.SignUp.route) {

DisposableEffect(key1 = authorizationViewModel.signUpState){
    onDispose {
        signUpState.value.error = null
        signUpState.value.isLoading = false
        signUpState.value.successful = null
    }
}

    DisposableEffect(key1 = gmailAuthState){
        onDispose {
            gmailAuthState.value.errorWhileAuth = null
            gmailAuthState.value.isLoading = false
            gmailAuthState.value.successful = null
        }
    }


            SignUpScreen(navigateToLogin = { navController.navigate(ScreensRoute.SignIn.route) },
               onGoogleLogoClick = {
                    navigationScope.launch{
                        launcherGmailSignIn.launch(
                            IntentSenderRequest.Builder(
                                authorizationViewModel.gmailSignUp()
                            ).build()
                        )
                    }
                }
            , navigateToEmailVerificationScreen = {
                        name,email,password->
                    navController.navigate("email_verification_screen/$name/$email/$password")
                }
            )


                LaunchedEffect(key1 = gmailAuthState.value.successful){
                    if(gmailAuthState.value.successful != null){
                        navController.navigate(ScreensRoute.News.route)
                        Log.d("TAG","Gmail auth works correct")

                    }
                }


            }


composable(
    ScreensRoute.EmailVerification.route,
arguments = listOf(
    navArgument("name"){
        type = NavType.StringType
    },
            navArgument("email"){
        type = NavType.StringType
    },
            navArgument("password"){
        type = NavType.StringType
    }
)
    ){

    val name = it.arguments?.getString("name") ?: ""
    val email = it.arguments?.getString("email") ?: ""
    val password = it.arguments?.getString("password") ?: ""


    Log.d("TAG","Retrieved data : $email , $password, $name")


    EmailVerificationScreen(
      email = email,
      name = name,
      password = password,
        navigateToLoginScreen = {
          navController.navigate(ScreensRoute.SignIn.route)
        },
        userBackToSignUpAndDeleteAccount = {
               profileViewModel.deleteUserFromFirebase()
               profileViewModel.signOutFromDefaultAccount()
               navController.navigate(ScreensRoute.SignUp.route)
        }
    )
}



        composable(ScreensRoute.SignIn.route){

            LaunchedEffect(key1 = gmailAuthState.value.errorWhileAuth) {
                if(gmailAuthState.value.errorWhileAuth != null) {
                    Toast.makeText(
                        context, gmailAuthState.value.errorWhileAuth, Toast.LENGTH_SHORT
                    ).show()
                    }

            }

            LaunchedEffect(key1 = gmailAuthState.value.successful){
                if(gmailAuthState.value.successful != null){
                    navController.navigate(ScreensRoute.News.route)
                    Log.d("TAG","Gmail auth works correct")

                }
            }





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
                },
            )
        }


        composable(
            route = ScreensRoute.News.route,
        ) {


            NewsScreen(
                onProfileClick = {
                    navController.navigate(ScreensRoute.Profile.route)
                }
            )
        }

        composable(ScreensRoute.ErrorWhileDisplayingNews.route){
            val errorWhileDisplayingNews = it.arguments?.getString("error") ?: ""

            NewsErrorScreen(
                error = errorWhileDisplayingNews,
                goBackToNewsScreen = {
                    navController.popBackStack()
                }
                )
        }

        composable(InternetScreenRoutes.NoInternetScreen.route) {
            NoInternetScreen()
        }

        composable(InternetScreenRoutes.BadInternetScreen.route) {
            BadInternet()
        }

        composable(ScreensRoute.Profile.route){
            ProfileScreen(
             onSignOutClick = {
                 navigationScope.launch {
                     val isUserAuthorizedWithGoogleSignIn =
                         authorizationViewModel.authorizedUserWithGmail()
                     val authorizedUserEmail = dataStoreViewModel.getAlreadySignedInUserEmail()
                     val isUserSignedInWithEmailPassword =
                         localDataBaseViewModel.getSignInUser(authorizedUserEmail.toString())
                     if(isUserAuthorizedWithGoogleSignIn != null){
                         profileViewModel.signOutFromGmailAccount()
                         delay(50)
                         profileViewModel.deleteUserFromLocalStorage(
                             isUserSignedInWithEmailPassword
                         )
                         dataStoreViewModel.saveVerifiedUserToDataStore(false)
                         navController.navigate(ScreensRoute.SignUp.route)
                     }else if(isUserSignedInWithEmailPassword != null){
                         profileViewModel.signOutFromDefaultAccount()
                         delay(50)
                         profileViewModel.deleteUserFromLocalStorage(
                             isUserSignedInWithEmailPassword
                         )
                         dataStoreViewModel.saveVerifiedUserToDataStore(false)
                         navController.navigate(ScreensRoute.SignUp.route)
                     }
                 }
             }
            )
        }

    }
}