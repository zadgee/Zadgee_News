package domain.use_cases

import android.util.Patterns
import domain.state.UserDataValidationResult

class ValidateUsersPasswordUseCase {
    fun isPasswordValid(password:String): UserDataValidationResult {
        val containsNumbersAndLetters = password.any { it.isDigit() } && password.any{it.isLetter()}
        if(password.isEmpty()){
            return     UserDataValidationResult(
                successful = false,
                errorMessage = "Password cannot be empty"
            )
        }else if(password.length < 8){
            return  UserDataValidationResult(
                successful = false,
                errorMessage = "Password is too short"
            )
        }else if(!containsNumbersAndLetters){
            return UserDataValidationResult(
                successful = false,
                errorMessage = "Password should contains at least 1 letter and 1 digit"
            )
        }else if(Patterns.EMAIL_ADDRESS.matcher(password).matches()){
            return UserDataValidationResult(
                successful = false,
                errorMessage = "Invalid password"
            )
        }

        return UserDataValidationResult(
            successful = true
        )

    }
}
