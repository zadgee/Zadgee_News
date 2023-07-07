package domain.repository

import remote.api.NewsDisplayingModelDTO
import domain.state.NewsUiState

interface NewsDisplayingRepository {
    suspend fun getNews(): NewsUiState<NewsDisplayingModelDTO>
}