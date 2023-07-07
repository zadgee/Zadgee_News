package screens.viewmodels
import android.content.IntentSender
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import authorization.AuthorizationRepository
import authorization.models.AuthState
import authorization.models.DefaultAuthorizationData
import authorization.models.GmailAuthorizationUserData
import authorization.models.LoginState
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import const.AUTHENTICATION_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import di.annotations.GmailSignIn
import di.annotations.GmailSignUp
import domain.state.AuthorizationState
import domain.state.EmailVerificationState
import domain.state.SignUpState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import localDataSource.SavingUserModel
import localDataSource.SavingUserRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@HiltViewModel
class AuthorizationViewModel@Inject constructor(
    private val authorizationRepository: AuthorizationRepository,
    val oneTapClient: SignInClient,
    val firebaseAuth: FirebaseAuth,
    @GmailSignIn private val signIn: BeginSignInRequest,
    @GmailSignUp private val signUp: BeginSignInRequest,
    private val savingUserRepository:SavingUserRepository
) : ViewModel() {

    val gmailStateAuthorization = MutableStateFlow(AuthState())
    val signUpState = MutableStateFlow(SignUpState())
    val loginState = MutableStateFlow(LoginState())
    private  val _verificationEmailState = MutableStateFlow<EmailVerificationState>(EmailVerificationState.NotVerified)
    val verificationEmailState = _verificationEmailState.asStateFlow()



    fun gmailAuth(credential: AuthCredential){
        viewModelScope.launch(Dispatchers.IO){
            gmailStateAuthorization.emit(
                AuthState(
                    errorWhileAuth = null,
                    Loading = true,
                    successful = null
                )
            )

           when(val gmailAuth =authorizationRepository.gmailAuth(credential)){
              is AuthorizationState.Success<*> ->{
                   gmailStateAuthorization.emit(
                       AuthState(
                           errorWhileAuth = null,
                           Loading = false,
                           successful = gmailAuth.data.toString()
                       )
                   )
               }

               is AuthorizationState.Error<*> ->{
                   gmailStateAuthorization.emit(
                       AuthState(
                           errorWhileAuth = AUTHENTICATION_ERROR,
                           Loading = false,
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




     suspend fun authorizedUserWithGmail(): GmailAuthorizationUserData? {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null && currentUser.providerData.any { it.providerId == GoogleAuthProvider.PROVIDER_ID} ) {
            return GmailAuthorizationUserData(
                id = currentUser.uid,
                name = currentUser.displayName,
                picture = currentUser.photoUrl?.toString()
            )
        }
        return null
    }

    suspend fun loginUser(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            loginState.emit(
                LoginState(
                    errorWhileLogin = null,
                    Loading = true,
                    successful = null
                )
            )

            when (val loginResult = authorizationRepository.loginUser(email, password)) {
                is AuthorizationState.Success<*> -> {
                    val user = SavingUserModel().apply {
                        this.email = email
                        this.password = password
                    }

                    savingUserRepository.saveUserToLocalStorage(user)


                    loginState.emit(
                        LoginState(
                            errorWhileLogin = null,
                            Loading = false,
                            successful = loginResult.data.toString()
                        )
                    )
                }

                is AuthorizationState.Error<*> -> {
                    loginState.emit(
                        LoginState(
                            errorWhileLogin = AUTHENTICATION_ERROR,
                            Loading = false,
                            successful = null
                        )
                    )
                }
            }
        }
    }

    suspend fun registerUserByDefaultMethod(email: String, password: String, name: String) =

        viewModelScope.launch(Dispatchers.IO) {

        signUpState.emit(
            SignUpState(
                successful = null,
                isLoading = true,
                error = null
            )
        )

        try {
            val signInMethods = firebaseAuth.fetchSignInMethodsForEmail(email).await().signInMethods
            if (signInMethods.isNullOrEmpty()) {
                when (val signUpResult = authorizationRepository.registration(email, password, name)) {
                    is AuthorizationState.Success<*> -> {
                        signUpState.emit(
                            SignUpState(
                                successful = signUpResult.data.toString(),
                                isLoading = false,
                                error = null
                            )
                        )
                    }
                    is AuthorizationState.Error<*> -> {
                        signUpState.emit(
                            SignUpState(
                                successful = null,
                                isLoading = false,
                                error =  "User already exists"
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            signUpState.emit(
                SignUpState(
                    successful = null,
                    isLoading = false,
                    error =  "Something went wrong while checking if the user exists or not"
                )
            )
        }
    }

     fun isEmailVerifiedCorrectly() {
      viewModelScope.launch(Dispatchers.IO) {
          _verificationEmailState.value = EmailVerificationState.Loading
          val isEmailVerified = firebaseAuth.currentUser?.isEmailVerified
          if(isEmailVerified != null){
              _verificationEmailState.value = EmailVerificationState.Verified
          }else{
              _verificationEmailState.value = EmailVerificationState.NotVerified
          }
      }
    }

    suspend fun getSignInUser(): Boolean {
        return suspendCoroutine { continuation ->
            viewModelScope.launch(Dispatchers.IO) {
                val isUserAlreadySignIn = loginState.value.successful
                if (isUserAlreadySignIn != null) {
                    savingUserRepository.getUserByEmailAndPassword().let { user ->
                        continuation.resume(true)
                    }
                } else {
                    continuation.resume(false)
                }
            }
        }
    }


}