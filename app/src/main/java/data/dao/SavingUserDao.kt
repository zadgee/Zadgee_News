package data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.model.SavingUserModel

@Dao
interface SavingUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: SavingUserModel)

    @Delete
    suspend fun deleteUser(user:SavingUserModel?)

    @Query("SELECT * FROM User_Entity WHERE email = :email")
    suspend fun getUserByEmail(email:String): SavingUserModel?

}