package screens.screens

sealed class ScreensRoute(val route:String){
    object SignUp:ScreensRoute("sign_up")
    object SignIn:ScreensRoute("sign_in")
    object News:ScreensRoute("news_screen")
    object Profile:ScreensRoute("profile_screen")
    object ForgotPassword:ScreensRoute("forgot_password_screen")
    object EmailVerification:ScreensRoute("email_verification_screen")
}
