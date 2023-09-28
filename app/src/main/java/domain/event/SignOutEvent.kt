package domain.event

sealed class SignOutEvent {
    class Success<T>(val signOutEvent: T): SignOutEvent()
    class Error(val errorMessage:String?): SignOutEvent()
}