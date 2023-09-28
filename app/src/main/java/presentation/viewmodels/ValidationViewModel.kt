package presentation.viewmodels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import domain.models.UserDataValidation
import domain.event.UsersDataEvent
import domain.use_cases.ValidateEmailUseCase
import domain.use_cases.ValidateNameUseCase
import domain.use_cases.ValidateUsersPasswordUseCase



class ValidationViewModel:ViewModel() {
    private val validateEmail : ValidateEmailUseCase = ValidateEmailUseCase()
    private val validatePassword: ValidateUsersPasswordUseCase = ValidateUsersPasswordUseCase()
    private val validateNameUseCase: ValidateNameUseCase = ValidateNameUseCase()

    var validationFieldsState by mutableStateOf(
        UserDataValidation(
        emailError = null,
        passwordError = null,
        nameError = null,
    )
    )





    fun checkEvent(event: UsersDataEvent){
            when(event){
                is UsersDataEvent.EmailChanged->{
                    validationFieldsState = validationFieldsState.copy(
                        email = event.email
                    )
                }
                is UsersDataEvent.PasswordChanged->{
                    validationFieldsState = validationFieldsState.copy(
                        password = event.password
                    )
                }

                is UsersDataEvent.NameChanged->{
                    validationFieldsState = validationFieldsState.copy(
                        name = event.name
                    )
                }

                is UsersDataEvent.UserEnteredAllData->{
                    validationFieldsState = validationFieldsState.copy(
                        name = event.name.toString(),
                        email = event.email,
                        password = event.password
                    )
                    checkData(event.name.toString(),event.email,event.password)
                }
            }
    }


    private fun checkData(name:String, email:String, password:String ): Boolean {
        val enteredEmail = validateEmail.isEmailValid(email)
        val enteredPassword = validatePassword.isPasswordValid(password)
        val enteredName = validateNameUseCase.isNameValid(name)
        val hasError = listOf(
            enteredEmail,
            enteredPassword,
            enteredName
        ).any {
            !it.successful
        }
        validationFieldsState = if (hasError) {
            validationFieldsState.copy(
                emailError = enteredEmail.errorMessage,
                passwordError = enteredPassword.errorMessage,
                nameError = enteredName.errorMessage
            )
        } else {
            validationFieldsState.copy(
                emailError = null,
                passwordError = null,
                nameError = null
            )
        }
        return hasError
    }





    sealed class ValidationConfirmEvent{
        object Success: ValidationConfirmEvent()
    }
}