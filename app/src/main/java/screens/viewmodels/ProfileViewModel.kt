package screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import authorization.AuthorizationRepository
import authorization.SignOutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.state.SignOutResult
import domain.state.SignOutState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel@Inject constructor(
    private val signOutRepository: SignOutRepository):ViewModel() {

    val stateSignOut = MutableStateFlow(SignOutResult())

    fun signOutFromDefaultAccount(){
        viewModelScope.launch(Dispatchers.IO) {
            SignOutResult(
                isLoading = true,
                error = null,
                successful = null
            )


            when(val signOutFromDefaultAccountResult =signOutRepository.signOutWhileUsingDefaultAuth()){
                is SignOutState.Success<*> ->{
                    stateSignOut.emit(
                        SignOutResult(
                            isLoading = false,
                            error = null,
                            successful = signOutFromDefaultAccountResult.signOutEvent.toString()
                        )
                    )
                }

                is SignOutState.Error<*> ->{
                    stateSignOut.emit(
                        SignOutResult(
                            isLoading = false,
                            error = null,
                            successful = null
                        )
                    )
                }
            }
        }
    }


    fun signOutWithGmailUsing(){
        viewModelScope.launch(Dispatchers.IO) {
            SignOutResult(
                isLoading = true,
                error = null,
                successful = null
            )


            when(val signOutFromGmailAccount =signOutRepository.signOutWithGmail()){
                is SignOutState.Success<*> ->{
                    stateSignOut.emit(
                        SignOutResult(
                            isLoading = false,
                            error = null,
                            successful = signOutFromGmailAccount.signOutEvent.toString()
                        )
                    )
                }

                is SignOutState.Error<*> ->{
                    stateSignOut.emit(
                        SignOutResult(
                            isLoading = false,
                            error = null,
                            successful = null
                        )
                    )
                }
            }
        }
    }

}