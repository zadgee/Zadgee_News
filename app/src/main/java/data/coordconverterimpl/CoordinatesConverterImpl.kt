package data.coordconverterimpl

import android.util.Log
import data.model.GeoCodingComponent
import data.model.GeoCodingModel
import domain.api.GeoCodingAPI
import domain.coordinatesconverter.CoordinatesConverter
import retrofit2.Response
import javax.inject.Inject

class CoordinatesConverterImpl@Inject constructor(
    private val api: GeoCodingAPI
):CoordinatesConverter {
    override suspend fun convertCoordinatesToCountryCode(
        coordinates: String
    ): Response<GeoCodingModel> {
        val response = api.convertCoordinatesToCountryCode(coordinates)
        if(!response.isSuccessful){
            Log.d("TAG","Error occurred: ${response.code()} && ${response.message()}")
        }

        return response
    }
}