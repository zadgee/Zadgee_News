package presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.use_cases.GetAlreadySignedInUserEmailUseCase
import domain.use_cases.GetSignedUpUserUseCase
import domain.use_cases.SaveUserEmailWhileSignInUseCase
import domain.use_cases.SaveVerifiedUserToDataStoreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel@Inject constructor(
private val getAlreadySignedInUserEmailUseCase: GetAlreadySignedInUserEmailUseCase,
private val getSignedUpUserUseCase: GetSignedUpUserUseCase,
private val saveUserEmailWhileSignInUseCase: SaveUserEmailWhileSignInUseCase,
private val saveVerifiedUserToDataStoreUseCase: SaveVerifiedUserToDataStoreUseCase
):ViewModel() {




       suspend fun getAlreadySignedInUserEmail():String?{
        return getAlreadySignedInUserEmailUseCase.getAlreadySignedInUserEmail()
     }

    suspend fun getSignedUpUser():Boolean?{
        return getSignedUpUserUseCase.getSignedUpUser()
    }

    fun saveUserEmailWhileSignIn(email: String){
        viewModelScope.launch(Dispatchers.IO){
            saveUserEmailWhileSignInUseCase.saveUserEmailWhileSignIn(email)
        }
    }

    fun saveVerifiedUserToDataStore(signedUpUser:Boolean){
        viewModelScope.launch(Dispatchers.IO){
            saveVerifiedUserToDataStoreUseCase.saveSignedUpUser(signedUpUser)
        }
    }

}