package domain.event

sealed class AuthorizationEvent{
    class Success<T>(val data: T): AuthorizationEvent()
    class Error<T>(val errorMessage:T): AuthorizationEvent()
}