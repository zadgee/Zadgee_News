package validation

sealed class UsersDataEvent{
    data class EmailChanged(val email:String):UsersDataEvent()
    data class PasswordChanged(val password:String):UsersDataEvent()
    data class NameChanged(val name:String):UsersDataEvent()
    object UserEnteredAllData:UsersDataEvent()
}

