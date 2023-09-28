package presentation.screens.mainscreens.signupscreen
import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import domain.event.UsersDataEvent
import presentation.viewmodels.AuthorizationViewModel
import presentation.viewmodels.ValidationViewModel
import presentation.views.LoadingEvent


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SignUpScreen(
    navigateToLogin: () -> Unit,
    onGoogleLogoClick: () -> Unit,
    navigateToEmailVerificationScreen: (name:String,email:String,password:String) -> Unit,
                 ) {

    val context = LocalContext.current.applicationContext
    val validationViewModel = hiltViewModel<ValidationViewModel>()
    val checkForValidationState = validationViewModel.validationFieldsState
    val authViewModel = hiltViewModel<AuthorizationViewModel>()
    val signUpState = authViewModel.signUpState.collectAsState()
    val gmailAuthState = authViewModel.gmailStateAuthorization.collectAsState()
    val emailError = checkForValidationState.emailError
    val passwordError = checkForValidationState.passwordError
    val nameError = checkForValidationState.nameError
    val name = checkForValidationState.name
    val email = checkForValidationState.email
    val password = checkForValidationState.password


    LaunchedEffect(key1 = signUpState.value.error) {
        when {
            signUpState.value.error != null -> {
                Toast.makeText(context, "${signUpState.value.error}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(
                key1 = signUpState.value.successful != null &&
                nameError == null &&
                emailError == null && passwordError == null
    ) {
        if(
            signUpState.value.successful != null &&
            nameError == null &&
            emailError == null && passwordError == null
            ){
            authViewModel.sendEmailVerificationLetter()
            Toast.makeText(context,
                "We've sent you email verification letter",
                Toast.LENGTH_SHORT).show()
            navigateToEmailVerificationScreen(name, email, password)
        }
    }


    if(gmailAuthState.value.isLoading || signUpState.value.isLoading){
        LoadingEvent()
    }

    SignUpScreenContent(
        checkForValidationState = checkForValidationState,
        navigateToLogin = navigateToLogin,
        onGoogleLogoClick = onGoogleLogoClick,
        email = email,
        password = password,
        name = name,
        onSignUpButtonClick = {
            validationViewModel.checkEvent(UsersDataEvent.UserEnteredAllData(name,email,password))
            authViewModel.registerUserByDefaultMethod(email, password, name)
        },
        onPasswordChanged = {validationViewModel.checkEvent(UsersDataEvent.PasswordChanged(it))},
        onEmailChanged = {validationViewModel.checkEvent(UsersDataEvent.EmailChanged(it))},
        onNameChanged = {validationViewModel.checkEvent(UsersDataEvent.NameChanged(it))}
        )



}