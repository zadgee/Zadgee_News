package data.remoteDataSource.repository
import android.annotation.SuppressLint
import android.util.Log
import com.google.android.gms.auth.api.identity.SignInClient
import domain.authorization.models.DefaultAuthorizationData
import domain.authorization.models.GmailAuthorizationUserData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import const.DB_COLLECTION_EMAIL_PASSWORD
import domain.authorization.repository.AuthorizationRepository
import domain.event.AuthorizationEvent
import domain.event.ReloadingUserEvent
import domain.event.SignUpEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthorizationRepositoryImpl@Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val oneTapClient: SignInClient,
) : AuthorizationRepository {

    override val user: FirebaseUser? get() = firebaseAuth.currentUser



    override suspend fun gmailAuth(credential: AuthCredential): AuthorizationEvent {
        return try {
            AuthorizationEvent.Success(data = correctGmailAuthorization(credential))
        } catch (e: Exception) {
            AuthorizationEvent.Error(errorMessage = "Something went wrong while auth")
        }
    }

    private suspend fun correctGmailAuthorization(credential: AuthCredential){
        val result = firebaseAuth.signInWithCredential(credential).await()
        val email = result.user?.email
        val isNewUser = result.additionalUserInfo?.isNewUser ?: false

        if (email != null && isNewUser) {
            addGmailUserToFireStore()
        }

        AuthorizationEvent.Success(data = result.toString())
    }


    private fun addGmailUserToFireStore() {
        user?.apply {
            val user = GmailAuthorizationUserData(
                id = uid,
                name = displayName,
                picture = photoUrl.toString()
            )
            val db = Firebase.firestore
            val userRef = db.collection("authorize_user_with_gmail")
            userRef.document().set(user).addOnSuccessListener {
                Log.d("QQQ","User have been successfully written to FireStore")
            }.addOnFailureListener {
                Log.w("QQQ","Error while writing user to FireStore")
            }
        }
    }



    override suspend fun registration(email: String, password: String,name:String): SignUpEvent {
        return try {
            SignUpEvent.Success(data = signUpUserCorrectly(email,password))
        } catch (e: Exception) {
            SignUpEvent.Error(
                errorMessage = e.message
            )
        }
    }

    private suspend fun signUpUserCorrectly(email: String, password: String): SignUpEvent {
        val isUserAlreadyCreatedAccountInFirebase =
            firebaseAuth.fetchSignInMethodsForEmail(email).await().signInMethods

        if(isUserAlreadyCreatedAccountInFirebase.isNullOrEmpty()){
            SignUpEvent.Error(errorMessage = "User already exists")
        }
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        return SignUpEvent.Success(data = result)

    }


    @SuppressLint("SuspiciousIndentation")
   override suspend  fun addUserToFireStore(password: String, name: String, email: String){
        val user = DefaultAuthorizationData(
            name = name,
            email = email,
            password = password.hashCode().toString()
        )
        val db = Firebase.firestore
        val userRef = db.collection("authorize_user_with_email_password")
        userRef.document().set(user).addOnSuccessListener {
            Log.d("QQQ","User have been successfully written to FireStore")
        }.addOnFailureListener {
            Log.w("QQQ","Error while writing user to FireStore, details : $it")
        }
    }




    @SuppressLint("SuspiciousIndentation")
    override suspend fun loginUser(email: String, password: String): AuthorizationEvent {
      return try {
          val authRes =  firebaseAuth.signInWithEmailAndPassword(email, password).await()
          AuthorizationEvent.Success(
              data = authRes
          )
      } catch (e:FirebaseAuthInvalidUserException){
          AuthorizationEvent.Error(
              errorMessage = "Invalid email or user does not exist"
          )
      }catch (e:FirebaseAuthInvalidCredentialsException){
          AuthorizationEvent.Error(
              errorMessage = "Invalid data"
          )
      }catch (e:Exception){
          AuthorizationEvent.Error(errorMessage = e.message)
      }
    }

    override suspend fun getUserNameFromFireStore(email: String): String {
        val collection = db.collection(DB_COLLECTION_EMAIL_PASSWORD)
        val query = collection.whereEqualTo("email", email).get().await()

        return if (!query.isEmpty) {
            val userName = query.toObjects(DefaultAuthorizationData::class.java)[0].name
            Log.d("TAG", userName)
            return userName
        } else {
            "failed"
        }
    }



    override suspend fun reloadUser(): ReloadingUserEvent {
        return try {
            val result = user?.reload()?.await()
            ReloadingUserEvent.Success(result)
        }catch (e:Exception){
            ReloadingUserEvent.Error(e.message)
        }
    }

    override suspend fun sendEmailVerificationLetter(): SignUpEvent {
        return try {
          val task = user?.sendEmailVerification()
            SignUpEvent.Success(data = task)
        }catch (e:Exception){
            SignUpEvent.Error(errorMessage = e.message)
        }
    }




    override fun signOutWhileUsingDefaultAuthorization() {
        try {
           firebaseAuth.signOut()
            Log.d("TAG","User was signed out")
       } catch (e:Exception){
           Log.d("TAG","${e.message}")
       }
    }



    override suspend fun signOutWhileUsingGmailAuthorization() {
            try {
               firebaseAuth.signOut()
               delay(100)
               oneTapClient.signOut().await()
           }catch (e:Exception){
                Log.d("TAG","${e.message}")
           }
    }




    override suspend fun deleteUserFromFirebase() {
        try {
            firebaseAuth.currentUser?.delete()?.await()
        } catch (e:Exception){
        Log.d("TAG","${e.message}")
        }
    }




}