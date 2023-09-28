package domain.use_cases

import domain.coordinatesconverter.CoordinatesConverter
import javax.inject.Inject

class ConvertCoordinatesToCountryCodeUseCase @Inject constructor(
    private val coordinatesConverter: CoordinatesConverter
){
     suspend fun convertCoordinatesToCountryCode(coordinates: String): String? {
         return coordinatesConverter.convertCoordinatesToCountryCode(coordinates).body()?.
         results?.get(0)?.components?.countryCode
     }

}