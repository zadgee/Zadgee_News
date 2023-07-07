package validation

class ValidateUsersPassword {
    fun isPasswordValid(password:String):UserDataValidationResult{
        if(password.isEmpty()){
            return     UserDataValidationResult(
                successful = false,
                errorMessage = "Password cannot be empty"
            )
        }

        if(password.length < 8){
            return  UserDataValidationResult(
                successful = false,
                errorMessage = "Password is too short"
            )
        }

        val containsNumbersAndLetters = password.any { it.isDigit() } && password.any{it.isLetter()}

        if(!containsNumbersAndLetters){
            return UserDataValidationResult(
                successful = false,
                errorMessage = "Password should contains at least 1 letter and 1 digit"
            )
        }

        return UserDataValidationResult(
            successful = true
        )

    }
}
