package authorization.models

data class LoginState(
    val errorWhileLogin:String? = null,
    val successful:String? = null,
    val Loading : Boolean = false,
)
