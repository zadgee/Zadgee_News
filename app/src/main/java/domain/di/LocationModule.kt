package domain.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import data.coordconverterimpl.CoordinatesConverterImpl
import data.locationtrackerimpl.LocationTrackerImpl
import domain.api.GeoCodingAPI
import domain.coordinatesconverter.CoordinatesConverter
import domain.locationTracker.LocationTracker
import domain.use_cases.ConvertCoordinatesToCountryCodeUseCase
import domain.use_cases.GetUserLocationUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocationModule {

    @Provides
    @Singleton
    fun provideFusedLocationProvider(@ApplicationContext context:Context)
    :FusedLocationProviderClient{
      return  LocationServices.getFusedLocationProviderClient(context.applicationContext)
    }


    @Provides
    @Singleton
    fun provideLocationTracker(
        fusedLocationProviderClient: FusedLocationProviderClient,
        @ApplicationContext context: Context
    ): LocationTracker {
        return LocationTrackerImpl(
            fusedLocationProviderClient,
            context
        )
    }


    @Provides
    @Singleton
    fun provideGettingLocationUseCase(
        locationTracker: LocationTracker
    ): GetUserLocationUseCase {
        return GetUserLocationUseCase(
            locationTracker
        )

    }

}