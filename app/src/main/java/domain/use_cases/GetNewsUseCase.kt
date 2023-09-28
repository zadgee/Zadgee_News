package domain.use_cases

import androidx.paging.PagingData
import domain.models.NewsData
import domain.newsrepo.NewsDisplayingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsDisplayingRepository: NewsDisplayingRepository
){
      suspend fun getNews(code:String):Flow<PagingData<NewsData>>{
          return newsDisplayingRepository.getNews(code)
      }
}