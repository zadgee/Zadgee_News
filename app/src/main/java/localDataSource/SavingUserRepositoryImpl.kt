package localDataSource
import domain.state.AuthorizationState
import domain.state.SignOutState
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class SavingUserRepositoryImpl@Inject constructor(private val realm:Realm) : SavingUserRepository {

    override suspend fun getUserByEmailAndPassword(): AuthorizationState {
        return try {
            val user = realm.query<SavingUserModel>().asFlow()
            AuthorizationState.Success(user)
        }catch (e:Exception){
            AuthorizationState.Error(e.message)
        }
    }

    override suspend fun saveUserToLocalStorage(user: SavingUserModel):AuthorizationState{
       return try {
           val data = realm.write { copyToRealm(user) }
           AuthorizationState.Success(data)
       }catch (e:Exception){
         AuthorizationState.Error(e.message)
       }
    }

    override suspend fun deleteUserFromLocalStorage(id: ObjectId): SignOutState {
     return try {
         realm.write {
             val user = query<SavingUserModel>(query = "id == $0",id).first().find()
                SignOutState.Success( user?.let {
                    delete(it)
                })
         }
     }catch (e:Exception){
         SignOutState.Error(e.message)
     }
    }

}