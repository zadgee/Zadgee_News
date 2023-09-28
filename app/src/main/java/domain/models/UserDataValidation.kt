package domain.models

data class UserDataValidation(
    var email:String="",
    var password:String="",
    val emailError:String?,
    val passwordError:String?,
    var name : String = "",
    val nameError:String?
)
