package presentation.viewmodels
import android.content.IntentSender
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import domain.authorization.repository.AuthorizationRepository
import domain.authorization.state.GmailAuthState
import domain.authorization.models.GmailAuthorizationUserData
import domain.authorization.state.LoginState
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import const.AUTHENTICATION_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.workers.EmailVerificationWorker
import domain.di.annotations.GmailSignIn
import domain.di.annotations.GmailSignUp
import domain.event.AuthorizationEvent
import domain.event.ReloadingUserEvent
import domain.state.ReloadingUserResult
import domain.event.SignUpEvent
import domain.state.SignUpState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class AuthorizationViewModel@Inject constructor(
    private val authorizationRepository: AuthorizationRepository,
    val oneTapClient: SignInClient,
    private val firebaseAuth: FirebaseAuth,
    @GmailSignIn private val signIn: BeginSignInRequest,
    @GmailSignUp private val signUp: BeginSignInRequest,
    val workManager: WorkManager
) : ViewModel() {

    private val _gmailStateAuthorization = MutableStateFlow(GmailAuthState())
    val gmailStateAuthorization = _gmailStateAuthorization.asStateFlow()
    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()
    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()
    private val _reloadingUserState = MutableStateFlow(ReloadingUserResult())
    val reloadingUserState = _reloadingUserState.asStateFlow()
    val isUserVerifyEmail get() = authorizationRepository.user?.isEmailVerified?: false
    val request = OneTimeWorkRequestBuilder<EmailVerificationWorker>()
        .setInitialDelay(3, TimeUnit.MINUTES)
        .build()


    fun gmailAuth(credential: AuthCredential){
        viewModelScope.launch(Dispatchers.IO){
            _gmailStateAuthorization.emit(
                GmailAuthState(
                    errorWhileAuth = null,
                    isLoading = true,
                    successful = null
                )
            )

           when(val gmailAuth =authorizationRepository.gmailAuth(credential)){
              is AuthorizationEvent.Success<*> ->{
                   _gmailStateAuthorization.emit(
                       GmailAuthState(
                           errorWhileAuth = null,
                           isLoading = false,
                           successful = gmailAuth.data.toString()
                       )
                   )
               }

               is AuthorizationEvent.Error<*> ->{
                   _gmailStateAuthorization.emit(
                       GmailAuthState(
                           errorWhileAuth = AUTHENTICATION_ERROR,
                           isLoading = false,
                           successful = null
                       )
                   )
               }
           }
        }
    }






   suspend fun gmailSignIn(): IntentSender {
       val signInResult = oneTapClient.beginSignIn(signIn).await()
       return signInResult.pendingIntent.intentSender
    }


    suspend fun gmailSignUp(): IntentSender {
        val signUpResult = oneTapClient.beginSignIn(signUp).await()
        return signUpResult.pendingIntent.intentSender
    }




      fun authorizedUserWithGmail(): GmailAuthorizationUserData? {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null && currentUser.providerData.any
            { it.providerId == GoogleAuthProvider.PROVIDER_ID }
            ) {
            return GmailAuthorizationUserData(
                id = currentUser.uid,
                name = currentUser.displayName,
                picture = currentUser.photoUrl?.toString()
            )
        }
        return null
    }

    fun loginUser(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            _loginState.emit(
                LoginState(
                    errorWhileLogin = null,
                    isLoading = true,
                    successful = null,
                )
            )

            when (val loginResult = authorizationRepository.loginUser(email, password)) {
                is AuthorizationEvent.Success<*> -> {
                    _loginState.emit(
                        LoginState(
                            errorWhileLogin = null,
                            isLoading = false,
                            successful = loginResult.data,
                        )
                    )
                }

                is AuthorizationEvent.Error<*> -> {
                    _loginState.emit(
                        LoginState(
                            errorWhileLogin = loginResult.errorMessage,
                            isLoading = false,
                            successful = null,
                        )
                    )
                }
            }
        }
    }

    fun registerUserByDefaultMethod(email: String, password: String, name: String) =
        viewModelScope.launch(Dispatchers.IO) {
            _signUpState.emit(
                SignUpState(
                    successful = null,
                    isLoading = true,
                    error = null
                )
            )

            when (val signUpResult = authorizationRepository.registration(email, password, name)) {
                is SignUpEvent.Error<*> -> {
                    _signUpState.emit(
                        SignUpState(
                            successful = null,
                            isLoading = false,
                            error = signUpResult.errorMessage
                        )
                    )
                }

                is SignUpEvent.Success<*> -> {
                    _signUpState.emit(
                        SignUpState(
                            successful = signUpResult.data,
                            isLoading = false,
                            error = null
                        )
                    )
                }


            }
        }

    fun sendEmailVerificationLetter(){
        viewModelScope.launch(Dispatchers.IO) {

            _signUpState.emit(
                SignUpState(
                    isLoading = true,
                    successful = null,
                    error = null
                )
            )

            when(val result =authorizationRepository.sendEmailVerificationLetter()){
                is SignUpEvent.Error<*> ->{
                    _signUpState.emit(
                        SignUpState(
                            isLoading = false,
                            successful = null,
                            error = result.errorMessage.toString()
                        )
                    )
                }

                is SignUpEvent.Success<*>-> {
                    _signUpState.emit(
                        SignUpState(
                            isLoading = false,
                            successful = result.data.toString(),
                            error = null
                        )
                    )
                }
            }
        }
    }







    fun reloadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _reloadingUserState.emit(
                ReloadingUserResult(
                    successful = null,
                    error = null,
                    isLoading = true
                )
            )
            when (val event = authorizationRepository.reloadUser()) {
                is ReloadingUserEvent.Error -> {
                    _reloadingUserState.emit(
                        ReloadingUserResult(
                            successful = null,
                            error = event.error,
                            isLoading = false
                        )
                    )
                }
                is ReloadingUserEvent.Success<*> -> {
                    _reloadingUserState.emit(
                        ReloadingUserResult(
                            successful = event.data.toString(),
                            error = null,
                            isLoading = false
                        )
                    )

                    if (!isUserVerifyEmail) {
                        _reloadingUserState.emit(
                            ReloadingUserResult(
                                successful = null,
                                error = "Please, make sure that you verify email",
                                isLoading = false
                            )
                        )
                    }

                }
            }
        }
    }



    fun addUserToFireStoreWhenEmailVerified(password: String,name: String,email: String){
        viewModelScope.launch(Dispatchers.IO) {
            authorizationRepository.addUserToFireStore(password, name, email)
        }
    }


    suspend fun getUserNameByEmail(email: String): String {
      return authorizationRepository.getUserNameFromFireStore(email)
    }


}