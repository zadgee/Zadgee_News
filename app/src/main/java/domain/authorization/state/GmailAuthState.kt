package domain.authorization.state

data class GmailAuthState(
    var errorWhileAuth:String? = null,
    var successful:String? = null,
    var isLoading : Boolean = false,
)

