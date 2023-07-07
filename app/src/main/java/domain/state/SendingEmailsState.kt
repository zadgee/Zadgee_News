package domain.state

sealed class SendingEmailsState{
    class Success<T>(val emailList: T):SendingEmailsState()
    class Error<T>(val errorMessage:T):SendingEmailsState()
}
