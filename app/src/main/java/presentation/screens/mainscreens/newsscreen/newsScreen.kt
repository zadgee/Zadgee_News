package presentation.screens.mainscreens.newsscreen
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import presentation.screens.mainscreens.newsscreen.components.NewsScreenContent
import presentation.viewmodels.AuthorizationViewModel
import presentation.viewmodels.DataStoreViewModel
import presentation.viewmodels.NewsViewModel


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NewsScreen(
    onProfileClick:() -> Unit
){
    val authorizationViewModel = hiltViewModel<AuthorizationViewModel>()
    val newsViewModel = hiltViewModel<NewsViewModel>()
    val dataStoreViewModel = hiltViewModel<DataStoreViewModel>()
    val requestMultiplePermissionsLauncher = rememberLauncherForActivityResult(
      ActivityResultContracts.RequestMultiplePermissions(),
    ){
        permissions->
        val isFineLocationGranted = permissions[
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ] ?: false


        val isCoarseLocationGranted = permissions[
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ] ?: false


        if(isFineLocationGranted && isCoarseLocationGranted) {
           newsViewModel.setPrimaryNewsCode("USA")
        }
    }


    LaunchedEffect(Unit) {
        requestMultiplePermissionsLauncher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }


    val name = produceState(initialValue = "Loading..."){
        val email = dataStoreViewModel.getAlreadySignedInUserEmail()
        val userName = authorizationViewModel.getUserNameByEmail(email.toString())
        value = userName
    }.value


    NewsScreenContent(
        gmailAuthorizationUserData = authorizationViewModel.authorizedUserWithGmail(),
        onProfileClick = onProfileClick,
        name = name,
        viewModel = newsViewModel
    )

}