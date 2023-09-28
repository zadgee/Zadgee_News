package presentation.screens.mainscreens.loginscreen
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import data.model.SavingUserModel
import domain.event.UsersDataEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.viewmodels.AuthorizationViewModel
import presentation.viewmodels.DataStoreViewModel
import presentation.viewmodels.LocalDataBaseViewModel
import presentation.viewmodels.ValidationViewModel

@SuppressLint("StateFlowValueCalledInComposition", "SuspiciousIndentation")
@Composable
fun LoginScreen(
    navigateToNewsScreen: () -> Unit,
    signInWithGoogle:()->Unit
) {
    val context = LocalContext.current.applicationContext
    val validationViewModel = hiltViewModel<ValidationViewModel>()
    val checkForValidationState = validationViewModel.validationFieldsState
    val authViewModel = hiltViewModel<AuthorizationViewModel>()
    val dataStoreViewModel = hiltViewModel<DataStoreViewModel>()
    val localDataBaseViewModel = hiltViewModel<LocalDataBaseViewModel>()
    val gmailAuthState = authViewModel.gmailStateAuthorization.collectAsState()
    val loginState = authViewModel.loginState.collectAsStateWithLifecycle()
    val userEmail = checkForValidationState.email
    val password = checkForValidationState.password
    val emailError = checkForValidationState.emailError
    val passwordError = checkForValidationState.passwordError
    val navigationScope = CoroutineScope(Dispatchers.Main)



    LaunchedEffect(key1 = loginState.value.errorWhileLogin) {
        if(loginState.value.errorWhileLogin != null) {
            Toast.makeText(context, "${loginState.value.errorWhileLogin}", Toast.LENGTH_SHORT)
                .show()
            }
    }


    LaunchedEffect(
        key1 =  loginState.value.successful != null
    ) {
        if(
            loginState.value.successful != null
            ) {
            navigateToNewsScreen()
        }

    }

    LoginScreenContent(
        loginState = loginState.value,
        gmailAuthState = gmailAuthState.value,
        checkForValidationState = checkForValidationState,
        signInWithGoogle = signInWithGoogle,
        email = userEmail,
        password = password,
        onSignInClick = {
            validationViewModel.checkEvent(UsersDataEvent.UserEnteredAllData(name = null, email = userEmail, password = password))
            if(emailError == null && passwordError == null){
               navigationScope.launch{
                   authViewModel.loginUser(userEmail, password)
                   val user = SavingUserModel(
                       email = userEmail,
                       password = password
                   )
                   delay(500)
                   localDataBaseViewModel.saveUserToLocalStorage(user)
                   delay(500)
                   dataStoreViewModel.saveUserEmailWhileSignIn(userEmail)
               }
            }
        },
        onEmailChanged = {validationViewModel.checkEvent(UsersDataEvent.EmailChanged(it))},
        onPasswordChanged = {validationViewModel.checkEvent(UsersDataEvent.PasswordChanged(it))}
    )


}
