package validation

data class UserDataValidationResult(
    val successful:Boolean,
    val errorMessage:String? = null
)
