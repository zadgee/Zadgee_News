package domain.state

sealed class AuthorizationState{
    class Success<T>(val data: T):AuthorizationState()
    class Error<T>(val errorMessage:T):AuthorizationState()
}