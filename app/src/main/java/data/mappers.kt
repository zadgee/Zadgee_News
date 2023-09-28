package data
import androidx.paging.PagingData
import domain.models.NewsData
import domain.models.NewsDisplayingDTO
import kotlinx.coroutines.flow.Flow

fun Flow<PagingData<NewsData>>.toNewsDisplayingDTO(): NewsDisplayingDTO {
     val newsList = mutableListOf<NewsData>()
     return NewsDisplayingDTO(newsComponents = ArrayList(newsList))
}