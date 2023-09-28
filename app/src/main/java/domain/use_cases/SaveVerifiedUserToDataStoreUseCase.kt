package domain.use_cases

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import javax.inject.Inject

class SaveVerifiedUserToDataStoreUseCase@Inject constructor(
    private val dataStore:DataStore<Preferences>,
    private val signedUpUserKey: Preferences.Key<Boolean>
) {

    suspend fun saveSignedUpUser(signedUpUser: Boolean) {
        dataStore.edit {
                pref->
            pref[signedUpUserKey] = signedUpUser
        }
    }


}