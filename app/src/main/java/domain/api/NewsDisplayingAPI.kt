package domain.api
import domain.models.NewsDisplayingDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsDisplayingAPI {
    @GET("top-headlines?apiKey=088314e505984671a0336ca82eb02bec")
    suspend fun getNews(
        @Query("country") countryCode: String,
        @Query("pageSize") pageSize: Int,
        @Query("pageIndex") pageIndex:Int
    ): Response<NewsDisplayingDTO>
}