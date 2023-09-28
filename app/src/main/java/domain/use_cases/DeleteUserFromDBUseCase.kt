package domain.use_cases

import data.model.SavingUserModel
import domain.authorization.repository.SavingUserRepository
import javax.inject.Inject

class DeleteUserFromDBUseCase@Inject constructor(
    private val repository: SavingUserRepository
){

    suspend fun deleteUserData(user:SavingUserModel?){
        repository.deleteUser(user)
    }

}