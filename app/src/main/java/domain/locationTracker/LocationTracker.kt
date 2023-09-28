package domain.locationTracker

import android.location.Location


interface LocationTracker {
    suspend fun getUserLocation(): Location?
}