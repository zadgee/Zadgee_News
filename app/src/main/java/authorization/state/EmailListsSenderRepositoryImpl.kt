package authorization.state

import com.google.firebase.auth.FirebaseAuth
import domain.state.SendingEmailsState
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EmailListsSenderRepositoryImpl@Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : EmailListsSenderRepository {
    override suspend fun sendEmailVerification(): SendingEmailsState{
        return try {
            val sendEmailVerificationToUserResult =
                firebaseAuth.currentUser?.sendEmailVerification()?.await().toString()
            SendingEmailsState.Success(
                emailList = sendEmailVerificationToUserResult
            )
        }catch (e:Exception){
            SendingEmailsState.Error(
                errorMessage = e.message
            )
        }
    }

    override suspend fun sendResetPasswordEmail(email: String): SendingEmailsState{
        return try {
            val sendPasswordResetEmailResult =
                firebaseAuth.sendPasswordResetEmail(email).await().toString()
            SendingEmailsState.Success(
                emailList = sendPasswordResetEmailResult
            )
        }catch (e:Exception){
            SendingEmailsState.Error(
                errorMessage = e.message
            )
        }
    }
}