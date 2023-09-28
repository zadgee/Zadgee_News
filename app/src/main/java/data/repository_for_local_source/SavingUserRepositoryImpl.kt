package data.repository_for_local_source
import data.dao.SavingUserDao
import data.model.SavingUserModel
import domain.authorization.repository.SavingUserRepository
import javax.inject.Inject

class SavingUserRepositoryImpl@Inject constructor(
    private val dao:SavingUserDao
): SavingUserRepository {
    override suspend fun saveUser(user: SavingUserModel){
      return  dao.saveUser(user)
    }

    override suspend fun deleteUser(user: SavingUserModel?) {
        return dao.deleteUser(user)
    }

    override suspend fun getUserByEmail(email: String): SavingUserModel? {
        return dao.getUserByEmail(email)
    }

}