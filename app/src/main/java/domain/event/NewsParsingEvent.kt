package domain.event

import domain.models.NewsDisplayingDTO


sealed class NewsParsingEvent{
    class Success(val data: NewsDisplayingDTO?): NewsParsingEvent()
    class Error(val errorMessage:String?): NewsParsingEvent()
}
