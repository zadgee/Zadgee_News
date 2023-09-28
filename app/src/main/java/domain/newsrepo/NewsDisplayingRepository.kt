package domain.newsrepo

import androidx.paging.PagingData
import domain.models.NewsData
import kotlinx.coroutines.flow.Flow


interface NewsDisplayingRepository {
    suspend fun getNews(
        code: String,
    ): Flow<PagingData<NewsData>>
}