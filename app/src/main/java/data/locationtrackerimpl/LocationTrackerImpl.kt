package data.locationtrackerimpl
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import domain.locationTracker.LocationTracker
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LocationTrackerImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val context: Context,
):LocationTracker {

    override suspend fun getUserLocation():Location? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            context.applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            context.applicationContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = context.applicationContext.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isGpsEnabled = locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        ) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)



        if(!hasAccessCoarseLocationPermission ||
            !hasAccessFineLocationPermission ||
            !isGpsEnabled){
            return null
        }

        return suspendCoroutine { continuation ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                fusedLocationProviderClient.getCurrentLocation(
                    LocationRequest.QUALITY_HIGH_ACCURACY,
                    null
                )
                    .addOnSuccessListener { location ->
                        Log.d("TAG","Location successfully received: $location")
                        continuation.resume(location)
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                        Log.d("TAG","Exception in getting user location: $exception")
                    }
                    .addOnCompleteListener {
                        task->
                        if (task.isComplete){
                            Log.d("TAG","Location task completed: ${task.result}")
                        }else if(task.isCanceled){
                            Log.d("TAG","Location task canceled : ${task.result}")
                        } else if(task.isSuccessful){
                            Log.d("TAG","Location task successful: ${task.result}")
                        }
                    }
            }else if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.R){
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                    Log.d("TAG","Location successfully received: $location")
                    continuation.resume(location)
                }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                        Log.d("TAG","Exception in getting user location: $exception")
                    }
                    .addOnCompleteListener {
                            task->
                        if (task.isComplete){
                            Log.d("TAG","Location task completed: ${task.result}")
                        }else if(task.isCanceled){
                            Log.d("TAG","Location task canceled : ${task.result}")
                        } else if(task.isSuccessful){
                            Log.d("TAG","Location task successful: ${task.result}")
                        }
                    }
            }
        }
     }
     }