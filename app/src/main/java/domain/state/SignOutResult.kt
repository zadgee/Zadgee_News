package domain.state

data class SignOutResult(
    val isLoading:Boolean = false,
    val error:String? = null,
    val successful:String? = null
)
