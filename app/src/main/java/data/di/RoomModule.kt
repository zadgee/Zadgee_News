package data.di
import android.content.Context
import androidx.room.Room
import const.USER_COLLECTION_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import data.dao.SavingUserDao
import data.db.SavingUserDataBase
import data.repository_for_local_source.SavingUserRepositoryImpl
import domain.authorization.repository.SavingUserRepository
import domain.use_cases.DeleteUserFromDBUseCase
import domain.use_cases.GetUserFromDBUseCase
import domain.use_cases.InsertUserDataToDBUseCase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

   @Provides
   @Singleton
   fun provideRoomDataBase(@ApplicationContext context: Context):SavingUserDataBase{
    return Room.databaseBuilder(
        context.applicationContext,
        SavingUserDataBase::class.java,
        USER_COLLECTION_DB
    ).build()
   }

    @Provides
    @Singleton
    fun provideUserDao(dataBase: SavingUserDataBase):SavingUserDao{
        return dataBase.savingUserDao()
    }


    @Provides
    @Singleton
    fun provideSavingUserRepository(userDao: SavingUserDao):SavingUserRepository{
        return SavingUserRepositoryImpl(
            userDao
        )
    }


    @Provides
    @Singleton
    fun provideDeleteUserUseCase(repository: SavingUserRepository):DeleteUserFromDBUseCase{
        return DeleteUserFromDBUseCase(
            repository
        )
    }

    @Provides
    @Singleton
    fun provideInsertUserUseCase(repository: SavingUserRepository):InsertUserDataToDBUseCase{
        return InsertUserDataToDBUseCase(
            repository
        )
    }

    @Provides
    @Singleton
    fun provideGetUserUseCase(repository: SavingUserRepository): GetUserFromDBUseCase {
        return GetUserFromDBUseCase(
            repository
        )
    }


}