package domain.authorization.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import domain.event.AuthorizationEvent
import domain.event.ParsingUserNameFromFireStoreEvent
import domain.event.ReloadingUserEvent
import domain.event.SignUpEvent


interface AuthorizationRepository {
    suspend fun gmailAuth(credential: AuthCredential): AuthorizationEvent
    suspend fun registration(email:String,password:String,name:String): SignUpEvent
    suspend fun loginUser(email: String,password: String): AuthorizationEvent
    suspend fun getUserNameFromFireStore(email: String):String
    suspend fun reloadUser(): ReloadingUserEvent
    suspend fun sendEmailVerificationLetter(): SignUpEvent
    suspend fun addUserToFireStore(password: String, name: String, email: String)
    fun signOutWhileUsingDefaultAuthorization()
    suspend fun signOutWhileUsingGmailAuthorization()
    suspend fun deleteUserFromFirebase()
    val user:FirebaseUser?
}