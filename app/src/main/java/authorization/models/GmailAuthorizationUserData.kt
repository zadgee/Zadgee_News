package authorization.models

data class GmailAuthorizationUserData(
    val id:String,
    val name:String?,
    val picture:String? = null
)

data class DefaultAuthorizationData(
    val email: String = "",
    val password: String = "",
    val name: String = ""
)

