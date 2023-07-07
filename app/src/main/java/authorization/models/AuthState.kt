package authorization.models

data class AuthState(
    val errorWhileAuth:String? = null,
    val successful:String? = null,
    val Loading : Boolean = false,
)

