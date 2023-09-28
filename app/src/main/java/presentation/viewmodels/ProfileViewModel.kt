package presentation.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.model.SavingUserModel
import domain.authorization.repository.AuthorizationRepository
import domain.use_cases.DeleteUserFromDBUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel@Inject constructor(
    private val deleteUserFromDBUseCase: DeleteUserFromDBUseCase,
    private val authorizationRepository: AuthorizationRepository
):ViewModel() {

     fun signOutFromDefaultAccount(){
         viewModelScope.launch(Dispatchers.IO) {
             authorizationRepository.signOutWhileUsingDefaultAuthorization()
         }
     }


    fun signOutFromGmailAccount(){
        viewModelScope.launch(Dispatchers.IO) {
            authorizationRepository.signOutWhileUsingGmailAuthorization()
        }
    }


     fun deleteUserFromFirebase()  {
         viewModelScope.launch(Dispatchers.IO) {
             authorizationRepository.deleteUserFromFirebase()

         }
     }


    fun deleteUserFromLocalStorage(user:SavingUserModel?){
        viewModelScope.launch(Dispatchers.IO) {
            deleteUserFromDBUseCase.deleteUserData(user)
        }
    }

}