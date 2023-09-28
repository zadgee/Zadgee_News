package data.di
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import const.DATASTORE
import const.SIGNED_IN_USER_EMAIL
import const.SIGNED_UP_USER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import domain.use_cases.GetAlreadySignedInUserEmailUseCase
import domain.use_cases.GetSignedUpUserUseCase
import domain.use_cases.SaveUserEmailWhileSignInUseCase
import domain.use_cases.SaveVerifiedUserToDataStoreUseCase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE)
        private val SIGNED_UP_USER_KEY = booleanPreferencesKey(SIGNED_UP_USER)
        private val SIGNED_IN_USER_EMAIL_KEY = stringPreferencesKey(SIGNED_IN_USER_EMAIL)
    }


    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context):DataStore<Preferences>{
        return context.dataStore
    }


    @Provides
    @Singleton
    fun provideSignedUpUserKey(): Preferences.Key<Boolean> {
        return SIGNED_UP_USER_KEY
    }


    @Provides
    @Singleton
    fun provideSignedInUserEmailKey():Preferences.Key<String>{
        return SIGNED_IN_USER_EMAIL_KEY
    }


    @Provides
    @Singleton
    fun provideGetAlreadySignedInUserEmail(dataStore: DataStore<Preferences>):
            GetAlreadySignedInUserEmailUseCase{
        return GetAlreadySignedInUserEmailUseCase(
            dataStore
        )
    }


    @Provides
    @Singleton
    fun provideGetSignedUpUserUseCase(dataStore: DataStore<Preferences>):GetSignedUpUserUseCase{
        return GetSignedUpUserUseCase(
            dataStore
        )
    }


    @Provides
    @Singleton
    fun provideSaveVerifiedUserToDataStoreUseCase(
        dataStore: DataStore<Preferences>,
        signedUpUserKey: Preferences.Key<Boolean>
    ): SaveVerifiedUserToDataStoreUseCase {
        return SaveVerifiedUserToDataStoreUseCase(
            dataStore, signedUpUserKey
        )
    }

    
    @Provides
    @Singleton
    fun provideSaveUserEmailWhileSignInUseCase(
         dataStore: DataStore<Preferences>,
         signInEmailSaving: Preferences.Key<String>
    ):SaveUserEmailWhileSignInUseCase{
        return SaveUserEmailWhileSignInUseCase(
            dataStore, signInEmailSaving
        )
    }




}