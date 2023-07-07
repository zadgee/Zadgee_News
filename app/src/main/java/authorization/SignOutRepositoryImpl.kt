package authorization

import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import domain.state.SignOutState
import javax.inject.Inject

class SignOutRepositoryImpl@Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val oneTapClient: SignInClient,
) : SignOutRepository {

    override suspend fun signOutWithGmail(): SignOutState{
        return try {
            val signOut = oneTapClient.signOut().result.toString()
            SignOutState.Success(
                signOutEvent = signOut
            )
        }catch (e:Exception){
            SignOutState.Error(
                errorMessage = e.message
            )
        }
    }

    override suspend fun signOutWhileUsingDefaultAuth():SignOutState{
        return try {
            val signOutFromDefaultAccount = firebaseAuth.signOut().toString()
            SignOutState.Success(
                signOutEvent = signOutFromDefaultAccount
            )
        }catch (e:Exception){
            SignOutState.Error(
                errorMessage = e.message
            )
        }
    }

}