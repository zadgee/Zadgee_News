package domain.repository

import remote.api.NewsDisplayingAPI
import remote.api.NewsDisplayingModelDTO
import domain.state.NewsUiState
import javax.inject.Inject

class NewsDisplayingRepositoryImpl@Inject constructor(private val api: NewsDisplayingAPI) : NewsDisplayingRepository {
    override suspend fun getNews(): NewsUiState<NewsDisplayingModelDTO> {
    return try{
        NewsUiState.Success(
            data = api.getNews().data!!
        )
    }catch (e:Exception){
        e.printStackTrace()
        NewsUiState.Error(
            errorMessage = "${e.message}"
        )
    }
    }
    }