package domain.state

data class UserDataValidationResult(
    val successful:Boolean,
    val errorMessage:String? = null
)
