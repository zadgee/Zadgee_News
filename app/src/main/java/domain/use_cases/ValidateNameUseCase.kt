package domain.use_cases

import android.util.Patterns
import domain.state.UserDataValidationResult


class ValidateNameUseCase {

    fun isNameValid(name:String): UserDataValidationResult {
        val containsDigits = name.any {
            it.isDigit()
        }
        val firstChar = name.firstOrNull()

        if(name.isEmpty()){
            return UserDataValidationResult(
                successful = false,
                errorMessage = "Your name must not be empty"
            )
        }else if(containsDigits || name.length == 1){
            return UserDataValidationResult(
                successful = false,
                errorMessage = "Invalid name"
            )
        } else if(firstChar == null || !firstChar.isUpperCase()){
            return UserDataValidationResult(
                successful = false,
                errorMessage = "Why your name does not starting with capital letter?:)"
            )
        } else if(Patterns.EMAIL_ADDRESS.matcher(name).matches()){
            return UserDataValidationResult(
                successful = false,
                errorMessage = "Invalid name"
            )
        }

         return UserDataValidationResult(
            successful = true,
            errorMessage = null
        )
    }

}