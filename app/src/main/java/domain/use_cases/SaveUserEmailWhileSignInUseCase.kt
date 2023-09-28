package domain.use_cases

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import javax.inject.Inject

class SaveUserEmailWhileSignInUseCase@Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val signInEmailSaving: Preferences.Key<String>
) {

    suspend fun saveUserEmailWhileSignIn(email: String){
        dataStore.edit {
                pref->
            pref[signInEmailSaving] = email
        }
    }

}