package authorization

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import domain.state.AuthorizationState
import domain.state.SendingEmailsState
import domain.state.SignOutState

interface AuthorizationRepository {
    suspend fun gmailAuth(credential: AuthCredential):AuthorizationState
    suspend fun registration(email:String,password:String,name:String):AuthorizationState
    suspend fun loginUser(email: String,password: String):AuthorizationState
    val currentUser : FirebaseUser?
}