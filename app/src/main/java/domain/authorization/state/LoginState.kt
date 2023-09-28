package domain.authorization.state

data class LoginState(
    val errorWhileLogin:Any? = null,
    val successful:Any? = null,
    val isLoading : Boolean = false,
)
