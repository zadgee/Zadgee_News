package domain.state

import domain.models.NewsDisplayingDTO


data class NewsState(
    val error:String? = null,
    val isLoading: Boolean = false,
    val dto: NewsDisplayingDTO? = null
)
