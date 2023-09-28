package domain.api
import data.model.GeoCodingModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface GeoCodingAPI {
    @GET("json?&key=7e3dda65b76a423bab2b49df65fb66e3")
    suspend fun convertCoordinatesToCountryCode(
       @Query("q")
       coordinates: String
    ): Response<GeoCodingModel>
}