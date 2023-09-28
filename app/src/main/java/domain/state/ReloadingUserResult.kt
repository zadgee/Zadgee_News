package domain.state

data class ReloadingUserResult(
    val successful:String? = null,
    val isLoading:Boolean = false,
    val error:String? = null
)