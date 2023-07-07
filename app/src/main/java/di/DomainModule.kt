package di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import remote.api.NewsDisplayingAPI
import domain.repository.NewsDisplayingRepository
import domain.repository.NewsDisplayingRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsDisplayingAPI): NewsDisplayingRepository {
        return NewsDisplayingRepositoryImpl(
            api
        )
    }

}