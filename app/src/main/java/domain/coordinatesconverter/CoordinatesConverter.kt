package domain.coordinatesconverter
import data.model.GeoCodingModel
import retrofit2.Response


interface CoordinatesConverter {
    suspend fun convertCoordinatesToCountryCode(
        coordinates: String
    ):Response<GeoCodingModel>
}