package di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.security.MessageDigest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MessageDigestModule {

    @Provides
    @Singleton
    fun provideMessageDigest():MessageDigest{
        return MessageDigest.getInstance("SHA-256")
    }
}