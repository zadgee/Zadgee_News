package domain.use_cases

import android.util.Patterns
import domain.state.UserDataValidationResult

class ValidateEmailUseCase {

    fun isEmailValid(email:String): UserDataValidationResult {
        if(email.isEmpty()){
            return   UserDataValidationResult(
                successful = false,
                errorMessage = "Email cannot be empty"
            )
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return UserDataValidationResult(
                successful = false,
                errorMessage = "Your email is invalid"
            )
        }


        return UserDataValidationResult(
            successful = true
        )
    }

}