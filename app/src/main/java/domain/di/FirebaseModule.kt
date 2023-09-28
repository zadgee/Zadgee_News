package domain.di
import android.app.Application
import android.content.Context
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import domain.authorization.repository.AuthorizationRepository
import data.remoteDataSource.repository.AuthorizationRepositoryImpl
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import const.WebClientId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import domain.workers.EmailVerificationWorker
import domain.di.annotations.GmailSignIn
import domain.di.annotations.GmailSignUp


@Module
@InstallIn(ViewModelComponent::class)
class FirebaseModule {

    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @GmailSignIn
    fun provideSignInRequest() = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(WebClientId)
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    @Provides
    @GmailSignUp
    fun provideSignUpRequest() = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(WebClientId)
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()



    @Provides
    fun provideFirebaseStore() = Firebase.firestore


    @Provides
    fun provideOneTapClient(
        @ApplicationContext context: Context
    ) = Identity.getSignInClient(context)


    @Provides
    fun provideSignInOptions() =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WebClientId)
            .requestEmail()
            .requestProfile()
            .build()

    @Provides
    fun provideGoogleSignInClient(application: Application, options: GoogleSignInOptions)=
        GoogleSignIn.getClient(application,options)


    @Provides
    fun provideWorkManagerInstance(@ApplicationContext context:Context):WorkManager{
        return WorkManager.getInstance(context)
    }



    @Provides
    fun provideEmailVerificationWaiterWorker(
        @ApplicationContext context: Context,
        params: WorkerParameters,
        authorizationRepository:AuthorizationRepository
    ): EmailVerificationWorker {
        return EmailVerificationWorker(
            context, params,
            authorizationRepository
        )
    }


    @Provides
    fun provideAuthRealization(firebaseAuth: FirebaseAuth,
                               db: FirebaseFirestore,
                               oneTapClient: SignInClient
    ): AuthorizationRepository {
        return AuthorizationRepositoryImpl(
            firebaseAuth, db,
            oneTapClient
        )
    }


}