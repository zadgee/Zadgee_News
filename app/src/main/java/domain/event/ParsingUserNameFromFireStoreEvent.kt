package domain.event

sealed class ParsingUserNameFromFireStoreEvent{
    class Success<T>(val name:T?): ParsingUserNameFromFireStoreEvent()
    class Error<T>(val errorMessage:T?): ParsingUserNameFromFireStoreEvent()
}