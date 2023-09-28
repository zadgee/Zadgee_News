package presentation.screens.mainscreens.profilescreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import presentation.viewmodels.AuthorizationViewModel
import presentation.viewmodels.DataStoreViewModel

@Composable
fun ProfileScreen(
    onSignOutClick:()->Unit
){
    val dataStoreViewModel = hiltViewModel<DataStoreViewModel>()
    val authorizationViewModel = hiltViewModel<AuthorizationViewModel>()
    val name = produceState(initialValue = "...") {
        val email = dataStoreViewModel.getAlreadySignedInUserEmail()
        val userName = authorizationViewModel.getUserNameByEmail(email.toString())
        value = userName
    }.value

ProfileScreenContent(
    gmailAuthorizationUserData = authorizationViewModel.authorizedUserWithGmail(),
    name = name,
    onSignOutClick = onSignOutClick
)


}