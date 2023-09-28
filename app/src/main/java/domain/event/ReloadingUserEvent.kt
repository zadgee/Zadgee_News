package domain.event

sealed class ReloadingUserEvent{
    class Success<T>(val data:T): ReloadingUserEvent()
    class Error(val error:String?): ReloadingUserEvent()
}
