package domain.state

data class SignUpState(
    var successful:Any?=null,
    var error:Any?=null,
    var isLoading:Boolean=false,
)