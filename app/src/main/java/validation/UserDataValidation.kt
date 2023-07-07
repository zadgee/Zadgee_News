package validation

data class UserDataValidation(
    val email:String="",
    val password:String="",
    val emailError:String?,
    val passwordError:String?,
    val name : String = "",
    val nameError:String?
)
