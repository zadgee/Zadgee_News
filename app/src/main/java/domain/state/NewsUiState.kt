package domain.state



sealed class NewsUiState<T>(val data: T? = null, val errorMessage:String? = null){
    class Success<T>(data: T):NewsUiState<T>(data)
    class Error<T>(errorMessage:String):NewsUiState<T>(data = null,errorMessage)
}
