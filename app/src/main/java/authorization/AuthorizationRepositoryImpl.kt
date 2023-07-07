package authorization
import android.annotation.SuppressLint
import android.util.Log
import authorization.models.DefaultAuthorizationData
import authorization.models.GmailAuthorizationUserData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import domain.state.AuthorizationState
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthorizationRepositoryImpl@Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) : AuthorizationRepository {

    override suspend fun gmailAuth(credential: AuthCredential): AuthorizationState {
       return try {
       val result = firebaseAuth.signInWithCredential(credential).await()
       val isNewUser = result.additionalUserInfo?.isNewUser ?:false

       if(isNewUser){
        addGmailUserToFireStore()
       }

       AuthorizationState.Success(
        data = result.toString()
        )

    } catch (e:Exception){

    AuthorizationState.Error(
        errorMessage = e.message
    )
}
    }


    private suspend fun addGmailUserToFireStore() {
        firebaseAuth.currentUser?.apply {
            val user = GmailAuthorizationUserData(
                id = uid,
                name = displayName,
                picture = photoUrl.toString()
            )
            db.collection("authorize_user_with_gmail").document(firebaseAuth.currentUser?.uid!!).set(user)
                .addOnSuccessListener {
                    Log.d("TAG","user data successfully added to database: $it")
                }
                .addOnFailureListener {
                    Log.d("TAG","user data successfully added to database: $it")
                }
                .await()
        }
    }



    override suspend fun registration(email: String, password: String,name:String):AuthorizationState{
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.sendEmailVerification()
            val isUserVerifyHisEmail = currentUser?.isEmailVerified?:false
            if(isUserVerifyHisEmail){
                addUserToFireStore(password,name,email)
            }
            AuthorizationState.Success(
                data = result.toString()
            )
        } catch (e: Exception) {
            AuthorizationState.Error(
                errorMessage = e.message
            )
        }
    }



    @SuppressLint("SuspiciousIndentation")
    private suspend fun addUserToFireStore(password: String, name: String,email: String) {
            val user = DefaultAuthorizationData(
                    name = name,
                    email = email,
                    password = password
            )
                db.collection("authorize_user_with_email/password").document(firebaseAuth.currentUser?.uid!!).set(user)
                    .await()
    }


    @SuppressLint("SuspiciousIndentation")
    override suspend fun loginUser(email: String, password: String): AuthorizationState {
      return try {
          val authRes =  firebaseAuth.signInWithEmailAndPassword(email, password).await()
          AuthorizationState.Success(
              data = authRes.user.toString()
          )
      } catch (e:FirebaseAuthInvalidUserException){
          AuthorizationState.Error(
              errorMessage = "Invalid email or user does not exist"
          )
      }catch (e:FirebaseAuthInvalidCredentialsException){
          AuthorizationState.Error(
              errorMessage = "Invalid password"
          )
      }catch (e:Exception){
          AuthorizationState.Error(errorMessage = e.message)
      }
    }

    override val currentUser get() = firebaseAuth.currentUser
}