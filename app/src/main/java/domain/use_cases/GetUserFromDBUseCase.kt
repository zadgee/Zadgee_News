package domain.use_cases
import data.model.SavingUserModel
import domain.authorization.repository.SavingUserRepository
import javax.inject.Inject

class GetUserFromDBUseCase@Inject constructor(
    private val repository: SavingUserRepository,
) {

    suspend fun getUser(email:String):SavingUserModel?{
     return repository.getUserByEmail(email)
    }
}