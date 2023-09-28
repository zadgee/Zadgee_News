package presentation.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.model.SavingUserModel
import domain.use_cases.GetUserFromDBUseCase
import domain.use_cases.InsertUserDataToDBUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalDataBaseViewModel@Inject constructor(
    private val getUserFromDBUseCase: GetUserFromDBUseCase,
    private val insertUserDataToDBUseCase: InsertUserDataToDBUseCase
) : ViewModel(){


   suspend fun getSignInUser(email: String):SavingUserModel?{
      return getUserFromDBUseCase.getUser(email)
    }


    fun saveUserToLocalStorage(user: SavingUserModel){
        viewModelScope.launch(Dispatchers.IO) {
            insertUserDataToDBUseCase.insertUserDataToDB(user)
        }
    }

}