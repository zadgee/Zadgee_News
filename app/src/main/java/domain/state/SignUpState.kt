package domain.state

data class SignUpState(
    val successful:String?=null,
    val error:String?=null,
    val isLoading:Boolean=false
)