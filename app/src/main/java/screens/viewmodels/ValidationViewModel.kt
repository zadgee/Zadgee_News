package screens.viewmodels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import validation.UserDataValidation
import validation.UsersDataEvent
import validation.ValidateEmail
import validation.ValidateName
import validation.ValidateUsersPassword



class ValidationViewModel:ViewModel() {
    private val validateEmail : ValidateEmail = ValidateEmail()
    private val validatePassword: ValidateUsersPassword = ValidateUsersPassword()
    private val validateName: ValidateName = ValidateName()

    var validationFieldsState by mutableStateOf(UserDataValidation(
        emailError = null,
        passwordError = null,
        nameError = null
    ))

    private val validationEventChannel = Channel<ValidationConfirmEvent>()
    val validEvents = validationEventChannel.receiveAsFlow()



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
                    checkData()
                }
            }
    }


    private fun checkData(): Boolean {
        val enteredEmail = validateEmail.isEmailValid(validationFieldsState.email)
        val enteredPassword = validatePassword.isPasswordValid(validationFieldsState.password)
        val enteredName = validateName.isNameValid(validationFieldsState.name)
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
        object Success:ValidationConfirmEvent()
    }
}