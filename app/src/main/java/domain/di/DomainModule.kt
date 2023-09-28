package domain.di
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.coordconverterimpl.CoordinatesConverterImpl
import data.remoteDataSource.repository.NewsDisplayingRepositoryImpl
import domain.api.NewsDisplayingAPI
import domain.api.GeoCodingAPI
import domain.coordinatesconverter.CoordinatesConverter
import domain.newsrepo.NewsDisplayingRepository
import domain.use_cases.ConvertCoordinatesToCountryCodeUseCase
import domain.use_cases.GetNewsUseCase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DomainModule {


    @Provides
    @Singleton
    fun provideNewsDisplayingRepository(api:NewsDisplayingAPI):NewsDisplayingRepository{
        return NewsDisplayingRepositoryImpl(
            api
        )
    }



    @Provides
    @Singleton
    fun provideNewsUseCase(newsDisplayingRepository:NewsDisplayingRepository):GetNewsUseCase {
        return GetNewsUseCase(
            newsDisplayingRepository
        )
    }


    @Provides
    @Singleton
    fun provideGeoCodingRepository(api: GeoCodingAPI): CoordinatesConverter {
        return CoordinatesConverterImpl(
            api
        )
    }

    @Provides
    @Singleton
    fun provideCoordinatesConverterUseCase(
        coordinatesConverter: CoordinatesConverter
    ):ConvertCoordinatesToCountryCodeUseCase {
        return ConvertCoordinatesToCountryCodeUseCase(
            coordinatesConverter
        )

    }





}