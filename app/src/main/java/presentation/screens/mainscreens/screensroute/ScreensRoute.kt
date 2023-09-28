package presentation.screens.mainscreens.screensroute

sealed class ScreensRoute(val route:String){
    object Loading:ScreensRoute("loading_screen")
    object SignUp: ScreensRoute("sign_up_screen")
    object SignIn: ScreensRoute("sign_in_screen")
    object News: ScreensRoute("news_screen")
    object Profile: ScreensRoute("profile_screen")
    object ErrorWhileDisplayingNews: ScreensRoute("error_while_displaying_news/{error}")
    object EmailVerification: ScreensRoute("email_verification_screen/{name}/{email}/{password}")

}
