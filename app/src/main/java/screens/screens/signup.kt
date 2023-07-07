package screens.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.firebase.newsapp.R
import com.firebase.newsapp.ui.theme.spacing.customSpacing
import const.ALREADY_HAVE_ACCOUNT
import const.EMAIL
import const.ENTER_EMAIL
import const.ENTER_YOUR_PASSWORD
import const.NAME
import const.PASSWORD
import const.SIGN_UP
import const.YOUR_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import screens.viewmodels.AuthorizationViewModel
import screens.viewmodels.ValidationViewModel
import validation.UsersDataEvent


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SignUpScreen(modifier: Modifier = Modifier, navigateToLogin: () -> Unit,
                 navigateFromGoogleSignUp: () -> Unit,
                 navigateToVerifyEmail: () -> Unit
                 ) {
    val context = LocalContext.current
    val validationViewModel = hiltViewModel<ValidationViewModel>()
    val checkForValidationState = validationViewModel.validationFieldsState
    val authViewModel = hiltViewModel<AuthorizationViewModel>()
    val signUpState = authViewModel.signUpState


    Column(modifier.fillMaxSize(),
    horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {


        LaunchedEffect(key1 = authViewModel.gmailStateAuthorization){
            authViewModel.gmailStateAuthorization.collect{
                    result->
                when{
                    result.successful != null ->
                        Log.d("TAG","SIGN IN has been successfully done")

                    result.errorWhileAuth != null ->
                        Log.d("TAG","Unsuccessful SIGN IN")
                }
            }
        }

        LaunchedEffect(key1 = Unit){
                signUpState.collect{
                    currentState->
                    when{
                        currentState.error != null->{
                            Toast.makeText(context,"${currentState.error}",Toast.LENGTH_SHORT).show()
                        }

                        currentState.successful != null ->{
                          Log.d("TAG","User have been successfully sign up")
                        }
                    }
                }
        }



        if(authViewModel.gmailStateAuthorization.value.Loading){
            Box{
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        }

        if(signUpState.value.isLoading){
            Box{
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        }




        OutlinedTextField(
            value = checkForValidationState.name, onValueChange = {
                validationViewModel.checkEvent(UsersDataEvent.NameChanged(it))
            },
            modifier
                .fillMaxWidth()
                .align(CenterHorizontally),
            label = { Text(text = NAME,
                fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                color = Color.Black
            )
            },
            placeholder = { Text(text = YOUR_NAME,
                fontFamily = FontFamily(Font(R.font.maintextfontinapp)
                ),
                color = Color.Black
            )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                errorTextColor = Color.Black,
                unfocusedTextColor = Color.Black
                ),
            isError = checkForValidationState.nameError != null,
            )

        if(checkForValidationState.nameError != null){
            Text(
                text = checkForValidationState.nameError.toString(),
                color = Color.Red,
                fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                textAlign = TextAlign.Start
            )
        }



        Spacer(modifier.padding(customSpacing.current.medium))

        OutlinedTextField(
            value = checkForValidationState.email, onValueChange = {
                validationViewModel.checkEvent(UsersDataEvent.EmailChanged(it))
            },
            modifier
                .fillMaxWidth()
                .align(CenterHorizontally),
            label = { Text(text = EMAIL,
                fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                color = Color.Black
            )
            },
            placeholder = { Text(text = ENTER_EMAIL,
                fontFamily = FontFamily(Font(R.font.maintextfontinapp)
                ),
                color = Color.Black
            )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                errorTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            isError = checkForValidationState.emailError != null,

        )

   if(checkForValidationState.emailError != null){
        Text(
            text = checkForValidationState.emailError.toString(),
            color = Color.Red,
            fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
            textAlign = TextAlign.Start
        )
    }

        Spacer(modifier.padding(customSpacing.current.extraMedium))


        OutlinedTextField(
            value = checkForValidationState.password,
            onValueChange = {
                validationViewModel.checkEvent(UsersDataEvent.PasswordChanged(it))
            },
            modifier
                .fillMaxWidth()
                .align(CenterHorizontally),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            label = { Text(text = PASSWORD,
                fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                color = Color.Black
            )
            },
            placeholder = { Text(text = ENTER_YOUR_PASSWORD,
                fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                color = Color.Black)
            },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                errorTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            visualTransformation = PasswordVisualTransformation(),
            isError = checkForValidationState.passwordError != null,
        )
        if(checkForValidationState.passwordError != null){
           Text(
               text = checkForValidationState.passwordError,
               color = Color.Red,
               fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
               textAlign = TextAlign.Start
            )
      }

        Spacer(modifier.padding(customSpacing.current.medium))


        Button(
            onClick = {
                val email = checkForValidationState.email
                val password = checkForValidationState.password
                val name = checkForValidationState.name
                val emailError = checkForValidationState.emailError
                val nameError = checkForValidationState.nameError
                val passwordError = checkForValidationState.passwordError

                val scope = CoroutineScope(Dispatchers.Main)
                scope.launch {
                    validationViewModel.checkEvent(UsersDataEvent.UserEnteredAllData)
                    if( emailError == null &&
                        nameError == null &&
                        passwordError == null
                    ){
                        authViewModel.registerUserByDefaultMethod(
                            email = email,
                            password = password,
                            name = name
                        )

                        Log.d("TAG","data here :$name")
                        Log.d("TAG","data here :$email")
                        Log.d("TAG","data here :$password")
                        navigateToVerifyEmail()
                    }
                }
            },
            modifier
                .padding(customSpacing.current.medium)
                .fillMaxWidth(),
            colors  = ButtonDefaults.buttonColors(containerColor = Color.White),
        ){
            Text(
                text = SIGN_UP,
                color = Color.Black
            )
        }



        TextButton(onClick = navigateToLogin,
            modifier.padding(customSpacing.current.medium)){
            Text(text = ALREADY_HAVE_ACCOUNT,
                 fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                 fontWeight = FontWeight.Bold,
                 color = Color.White
            )
        }

        IconButton(
            onClick = navigateFromGoogleSignUp,
        ) {
            AsyncImage(model =
            "https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/" +
                    "588px-Google_%22G%22_Logo.svg.png?20230305195327",
                contentDescription = "Google Icon"
            )
        }
    }
}