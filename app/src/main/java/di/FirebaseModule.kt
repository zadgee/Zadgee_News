package di

import android.app.Application
import android.content.Context
import authorization.AuthorizationRepository
import authorization.AuthorizationRepositoryImpl
import authorization.SignOutRepository
import authorization.SignOutRepositoryImpl
import authorization.state.EmailListsSenderRepository
import authorization.state.EmailListsSenderRepositoryImpl
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
import di.annotations.GmailSignIn
import di.annotations.GmailSignUp
import java.security.MessageDigest
import javax.inject.Singleton

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
    fun provideAuthRealization(firebaseAuth: FirebaseAuth,
                               db: FirebaseFirestore,
    ): AuthorizationRepository {
        return AuthorizationRepositoryImpl(
            firebaseAuth, db
        )
    }

    @Provides
    fun provideEmailRepository(firebaseAuth:FirebaseAuth): EmailListsSenderRepository {
        return EmailListsSenderRepositoryImpl(
            firebaseAuth
        )
    }


    @Provides
    fun provideSignOutRepository(firebaseAuth:FirebaseAuth,oneTapClient:SignInClient): SignOutRepository {
        return SignOutRepositoryImpl(
            firebaseAuth,oneTapClient
        )
    }




}