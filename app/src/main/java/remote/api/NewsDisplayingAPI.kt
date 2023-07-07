package remote.api

import domain.state.NewsUiState
import retrofit2.http.GET

interface NewsDisplayingAPI {
    @GET("everything?q=keyword")
    suspend fun getNews():NewsUiState<NewsDisplayingModelDTO>
}