package domain.event

sealed class UsersDataEvent{
    data class EmailChanged(val email:String): UsersDataEvent()
    data class PasswordChanged(val password:String): UsersDataEvent()
    data class NameChanged(val name:String): UsersDataEvent()
    data class UserEnteredAllData(val name: String?,val email: String,val password: String):
        UsersDataEvent()
}

