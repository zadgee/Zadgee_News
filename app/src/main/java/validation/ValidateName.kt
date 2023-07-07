package validation


class ValidateName {

    fun isNameValid(name:String):UserDataValidationResult{
        if(name.isEmpty()){
            return UserDataValidationResult(
                successful = false,
                errorMessage = "Your name must not be empty"
            )
        }

       val containsDigits = name.any {
           it.isDigit()
       }

        if(containsDigits || name.length == 1){
            return UserDataValidationResult(
                successful = false,
                errorMessage = "Invalid name"
            )
        }

        val firstChar = name.firstOrNull()

        if(firstChar == null || !firstChar.isUpperCase()){
            return UserDataValidationResult(
                successful = false,
                errorMessage = "Why your name does not starting with capital letter?:)"
            )
        }

         return UserDataValidationResult(
            successful = true,
            errorMessage = null
        )
    }

}