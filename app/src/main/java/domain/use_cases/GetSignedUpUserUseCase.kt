package domain.use_cases

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import const.SIGNED_UP_USER
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetSignedUpUserUseCase@Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

     suspend fun getSignedUpUser():Boolean?{
        val dataStoreKey = booleanPreferencesKey(SIGNED_UP_USER)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

}