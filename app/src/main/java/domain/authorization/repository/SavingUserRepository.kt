package domain.authorization.repository

import data.model.SavingUserModel

interface SavingUserRepository {

    suspend fun saveUser(user: SavingUserModel)

    suspend fun deleteUser(user:SavingUserModel?)

    suspend fun getUserByEmail(email:String): SavingUserModel?
}