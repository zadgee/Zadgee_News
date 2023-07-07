package localDataSource
import domain.state.AuthorizationState
import domain.state.SignOutState
import org.mongodb.kbson.ObjectId

interface SavingUserRepository {
    suspend fun getUserByEmailAndPassword(): AuthorizationState
    suspend fun saveUserToLocalStorage(user: SavingUserModel): AuthorizationState
    suspend fun deleteUserFromLocalStorage(id:ObjectId):SignOutState
}