package di

import localDataSource.SavingUserModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import localDataSource.SavingUserRepository
import localDataSource.SavingUserRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RealmModule {

    @Provides
    @Singleton
    fun provideRealmDependency():Realm{
        val config = RealmConfiguration.Builder(schema = setOf(SavingUserModel::class))
            .compactOnLaunch() // optimize db size, while app launching
            .build()
       return Realm.open(config)
    }

    @Provides
    @Singleton
    fun provideRealmRepository(realm: Realm):SavingUserRepository{
        return SavingUserRepositoryImpl(
            realm
        )
    }


}