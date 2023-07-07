package localDataSource

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

 class SavingUserModel:RealmObject{
     @PrimaryKey
     var id:ObjectId = ObjectId()
     var email:String = ""
     var password:String = ""
 }
