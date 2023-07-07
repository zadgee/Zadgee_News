package domain.state

import remote.api.NewsDataDTO
import remote.api.NewsDisplayingModelDTO

data class NewsState(
    val error:String? = null,
    val isLoading : Boolean = false,
    val newsDisplay: NewsDisplayingModelDTO?= null,
    val newsData: NewsDataDTO?  = null
)
