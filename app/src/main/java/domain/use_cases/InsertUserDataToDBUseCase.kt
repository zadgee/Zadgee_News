package domain.use_cases

import data.model.SavingUserModel
import domain.authorization.repository.SavingUserRepository
import javax.inject.Inject

class InsertUserDataToDBUseCase@Inject constructor(
    private val repository: SavingUserRepository
) {

    suspend fun insertUserDataToDB(user:SavingUserModel) = repository.saveUser(user)

}