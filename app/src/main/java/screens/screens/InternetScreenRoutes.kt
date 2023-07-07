package screens.screens

sealed class InternetScreenRoutes(val route:String){
    object NoInternetScreen:InternetScreenRoutes("no_internet_screen")
    object BadInternetScreen:InternetScreenRoutes("bad_internet_screen")
}
