package data.remoteDataSource.repository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import const.PAGE_SIZE
import data.pagingSource.NewsRepoPagingSource
import domain.api.NewsDisplayingAPI
import domain.models.NewsData
import domain.newsrepo.NewsDisplayingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsDisplayingRepositoryImpl @Inject constructor(
    private val api:NewsDisplayingAPI
):NewsDisplayingRepository {

    override suspend fun getNews(code: String): Flow<PagingData<NewsData>> {
        return Pager(
                pagingSourceFactory = { NewsRepoPagingSource(
                    api = api,
                    code = code,
                ) },
                config = PagingConfig(
                    pageSize = PAGE_SIZE,
                    enablePlaceholders = false,
                    prefetchDistance = 1,
                )
            ).flow
    }

}