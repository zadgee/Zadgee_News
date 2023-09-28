package presentation.screens.mainscreens.signupscreen
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.firebase.newsapp.R
import presentation.newsapp.ui.theme.spacing.customDpOrSpSize
import const.AD_ID
import const.ALREADY_HAVE_ACCOUNT
import const.ENTER_EMAIL
import const.ENTER_YOUR_PASSWORD
import const.GOOGLE_LOGO
import const.SIGN_UP
import const.YOUR_NAME
import domain.models.UserDataValidation
import presentation.views.AdBanner


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreenContent(
    modifier: Modifier=Modifier,
    checkForValidationState: UserDataValidation,
    navigateToLogin: () -> Unit,
    onGoogleLogoClick: () -> Unit,
    email: String,
    password: String,
    name:String,
    onSignUpButtonClick:()->Unit,
    onNameChanged:(String)->Unit,
    onEmailChanged:(String)->Unit,
    onPasswordChanged:(String)->Unit,
    ){

val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = name, onValueChange = onNameChanged,
            modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            placeholder = { Text(
                text = YOUR_NAME,
                fontFamily = FontFamily(
                    Font(R.font.maintextfontinapp)
                ),
                color = Color.Black
            )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
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
            isError = checkForValidationState.nameError != null,
            singleLine = true,

        )

        if(checkForValidationState.nameError != null){
            Text(
                text = checkForValidationState.nameError,
                color = Color.Red,
                fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                textAlign = TextAlign.Start
            )
        }



        Spacer(modifier.padding(customDpOrSpSize.current.medium))

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
            singleLine = true,
            isError = checkForValidationState.emailError != null,
            )

        if(checkForValidationState.emailError != null){
            Text(
                text = checkForValidationState.emailError,
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
            isError = checkForValidationState.passwordError != null
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
                onSignUpButtonClick()
                keyboardController?.hide()
            },
            modifier = modifier
                .padding(customDpOrSpSize.current.medium)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(
                text = SIGN_UP,
                color = Color.Black
            )
        }



        TextButton(
            onClick = navigateToLogin,
            modifier.padding(customDpOrSpSize.current.medium)){

            Text(
                text = ALREADY_HAVE_ACCOUNT,
                fontFamily = FontFamily(Font(R.font.maintextfontinapp)),
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        AdBanner(id = AD_ID)

        IconButton(
            onClick = onGoogleLogoClick,
        ) {
            AsyncImage(model = GOOGLE_LOGO,
                contentDescription = "Google Icon"
            )
        }

    }
}