package domain.event

sealed class SignUpEvent{
    class Success<T>(val data: T): SignUpEvent()
    class Error<T>(val errorMessage:T): SignUpEvent()
}