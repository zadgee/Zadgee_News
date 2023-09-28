package domain.use_cases

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import const.SIGNED_IN_USER_EMAIL
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAlreadySignedInUserEmailUseCase@Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun getAlreadySignedInUserEmail(): String?{
        val dataStoreKey = stringPreferencesKey(SIGNED_IN_USER_EMAIL)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

}