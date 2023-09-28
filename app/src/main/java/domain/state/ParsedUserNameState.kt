package domain.state

data class ParsedUserNameState(
    val successful:String?= null,
    val isLoading:Boolean = false,
    val error:String?=null
)