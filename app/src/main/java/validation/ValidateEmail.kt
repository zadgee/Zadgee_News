package validation

import android.util.Patterns

class ValidateEmail{

    fun isEmailValid(email:String):UserDataValidationResult{
        if(email.isEmpty()){
            return   UserDataValidationResult(
                successful = false,
                errorMessage = "Email cannot be empty"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
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