package presentation.screens.mainscreens.loginscreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.authorization.state.GmailAuthState
import domain.authorization.state.LoginState
import coil.compose.AsyncImage
import com.firebase.newsapp.R
import presentation.newsapp.ui.theme.spacing.customDpOrSpSize
import const.AD_ID
import const.EMAIL
import const.ENTER_EMAIL
import const.ENTER_YOUR_PASSWORD
import const.GOOGLE_LOGO
import const.PASSWORD
import const.SIGN_IN
import domain.models.UserDataValidation
import presentation.views.AdBanner
import presentation.views.LoadingEvent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreenContent(
    modifier:Modifier=Modifier,
    loginState: LoginState,
    gmailAuthState: GmailAuthState,
    checkForValidationState: UserDataValidation,
    signInWithGoogle:()->Unit,
    email:String,
    password:String,
    onSignInClick:()->Unit,
    onEmailChanged:(String)->Unit,
    onPasswordChanged:(String)->Unit
                       ){

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if(loginState.isLoading || gmailAuthState.isLoading){
            LoadingEvent()
        }

        OutlinedTextField(
            value = email, onValueChange = onEmailChanged,
            modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            placeholder = { Text(text = ENTER_EMAIL,
                fontFamily = FontFamily(
                    Font(R.font.maintextfontinapp)
                ),
                color = Color.Black
            )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
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
            singleLine = true,
            )

        if(checkForValidationState.emailError != null){
            Text(
                text = checkForValidationState.emailError.toString(),
                color = Color.Red,
                fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                textAlign = TextAlign.Start
            )
        }

        Spacer(modifier.padding(customDpOrSpSize.current.extraMedium))


        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChanged,
            modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
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




        Spacer(modifier.padding(customDpOrSpSize.current.medium))



        Button(
            onClick = {
             onSignInClick()
             keyboardController?.hide()
            },
            modifier
                .padding(customDpOrSpSize.current.medium)
                .fillMaxWidth(),
            colors  = ButtonDefaults.buttonColors(containerColor = Color.White),
        ){
            Text(
                text = SIGN_IN,
                color = Color.Black
            )
        }





        IconButton(
            onClick = signInWithGoogle,
        ) {
            AsyncImage(model = GOOGLE_LOGO,
                contentDescription = "Google Icon"
            )
        }

        Spacer(modifier = modifier.padding(customDpOrSpSize.current.small))


        AdBanner(id = AD_ID)


    }

}