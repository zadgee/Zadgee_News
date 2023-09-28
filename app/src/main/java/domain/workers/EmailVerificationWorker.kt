package domain.workers
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import domain.authorization.repository.AuthorizationRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class EmailVerificationWorker @Inject constructor(
   context: Context, params: WorkerParameters,
   private val authorizationRepository:AuthorizationRepository
): CoroutineWorker(context,params) {

    private val isEmailVerified get () = authorizationRepository.user?.isEmailVerified?: false

    override suspend fun doWork(): Result {
        authorizationRepository.reloadUser()
        return if (isEmailVerified){
            delay(500)
            Result.success()
        }else{
            authorizationRepository.deleteUserFromFirebase()
            authorizationRepository.signOutWhileUsingDefaultAuthorization()
            Result.failure()
        }
    }

}