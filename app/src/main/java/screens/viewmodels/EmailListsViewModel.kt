package screens.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import authorization.state.EmailListsSenderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.state.SendingEmailListsResult
import domain.state.SendingEmailsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailListsViewModel@Inject constructor(
    private val emailListsSenderRepository: EmailListsSenderRepository
):ViewModel() {
    private  val _stateSendingEmailLists = MutableStateFlow(SendingEmailListsResult())
    val stateSendingEmailLists = _stateSendingEmailLists.asStateFlow()




    fun sendPasswordChangeEmail(email: String){
        viewModelScope.launch(Dispatchers.IO) {
            _stateSendingEmailLists.emit(
                SendingEmailListsResult(
                    isLoading = true,
                    isSended = null
                )
            )

            when(val emailVerificationResult = emailListsSenderRepository.sendResetPasswordEmail(email)){
                is SendingEmailsState.Success<*> ->{
                    _stateSendingEmailLists.emit(
                        SendingEmailListsResult(
                            isLoading = false,
                            isSended = emailVerificationResult.emailList.toString()
                        )
                    )
                }

                is SendingEmailsState.Error<*> ->{
                    _stateSendingEmailLists.emit(
                        SendingEmailListsResult(
                            isLoading = false,
                            isSended = null
                        )
                    )
                }
            }
        }
    }
}