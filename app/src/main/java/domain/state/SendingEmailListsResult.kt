package domain.state

data class SendingEmailListsResult(
    val isLoading:Boolean = false,
    val isSended:String? = null,
)
