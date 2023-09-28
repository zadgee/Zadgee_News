package data.db
import androidx.room.Database
import androidx.room.RoomDatabase
import data.dao.SavingUserDao
import data.model.SavingUserModel

@Database(entities = [SavingUserModel::class], version = 1, exportSchema = false)
abstract class SavingUserDataBase:RoomDatabase() {
    abstract fun savingUserDao():SavingUserDao
}