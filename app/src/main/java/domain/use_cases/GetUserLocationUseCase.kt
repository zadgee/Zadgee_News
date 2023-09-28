package domain.use_cases
import android.location.Location
import domain.locationTracker.LocationTracker
import javax.inject.Inject

class GetUserLocationUseCase@Inject constructor(
    private val locationTracker: LocationTracker
) {
      suspend fun getUserLocation(): Location?{
          return locationTracker.getUserLocation()
      }
}