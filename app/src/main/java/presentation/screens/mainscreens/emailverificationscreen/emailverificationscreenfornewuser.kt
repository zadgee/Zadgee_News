package presentation.screens.mainscreens.emailverificationscreen
import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.work.WorkInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.viewmodels.AuthorizationViewModel
import presentation.viewmodels.DataStoreViewModel
import presentation.viewmodels.ProfileViewModel


@SuppressLint("SuspiciousIndentation")
@Composable
fun EmailVerificationScreen(
    name: String,
    email: String,
    password: String,
    navigateToLoginScreen: () -> Unit,
    userBackToSignUpAndDeleteAccount:() -> Unit
) {
    val context = LocalContext.current.applicationContext
    val authViewModel = hiltViewModel<AuthorizationViewModel>()
    val reloadState = authViewModel.reloadingUserState.collectAsStateWithLifecycle()
    val isEmailVerified  = authViewModel.isUserVerifyEmail
    val dataStoreViewModel = hiltViewModel<DataStoreViewModel>()
    val workManager = authViewModel.workManager
    val request = authViewModel.request
    val lifecycleOwner = LocalLifecycleOwner.current
    val succeededScope = CoroutineScope(Dispatchers.Main)

    workManager.enqueue(request)
    workManager.getWorkInfoByIdLiveData(request.id).observe(lifecycleOwner){
        workInfo->
        when(workInfo.state){
            WorkInfo.State.ENQUEUED ->  Log.d("TAG","Task is enqueued")
            WorkInfo.State.RUNNING ->  Log.d("TAG","Task is running")
            WorkInfo.State.SUCCEEDED -> {
                succeededScope.launch {
                    dataStoreViewModel.saveVerifiedUserToDataStore(true)
                    authViewModel.addUserToFireStoreWhenEmailVerified(password, name, email)
                    delay(1200)
                    navigateToLoginScreen()
                }
            }
            WorkInfo.State.FAILED -> {
                userBackToSignUpAndDeleteAccount()
            }
            WorkInfo.State.BLOCKED -> Log.d("TAG","Task is blocked")
            WorkInfo.State.CANCELLED -> Log.d("TAG","Task is cancelled")
        }
    }


    BackHandler(onBack = userBackToSignUpAndDeleteAccount)




    LaunchedEffect(key1 = reloadState.value.error){
        when {
            reloadState.value.error != null ->
                Toast.makeText(context, reloadState.value.error, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = reloadState.value.successful){
        if(isEmailVerified){
            dataStoreViewModel.saveVerifiedUserToDataStore(true)
            authViewModel.addUserToFireStoreWhenEmailVerified(password, name, email)
            workManager.cancelAllWork()
            delay(1200)
            navigateToLoginScreen()
        }else{
            Toast.makeText(context, "" +
                    "Please make sure, that you verify email" +
                    "", Toast.LENGTH_SHORT).show()
        }
    }






    EmailVerificationScreenContent(
        reloadState = reloadState.value,
        onAlreadyVerifiedClick = {
            authViewModel.reloadUser()
        }
     )
        }