package data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import const.EMAIL
import const.PASSWORD
import const.USER_ENTITY

@Entity(tableName = USER_ENTITY)
data class SavingUserModel(
    @PrimaryKey
    val id:Int = 0,
    @ColumnInfo(EMAIL)
    var email:String ="",
    @ColumnInfo(PASSWORD)
    var password:String = ""
)


