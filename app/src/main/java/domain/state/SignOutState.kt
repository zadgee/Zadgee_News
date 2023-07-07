package domain.state

sealed class SignOutState {
    class Success<T>(val signOutEvent: T):SignOutState()
    class Error<T>(val errorMessage:T):SignOutState()
}