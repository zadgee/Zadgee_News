package screens.internetcheckingscreens

import kotlinx.coroutines.flow.Flow

interface ConnectivityState{

    fun observeConnection(): Flow<Status>

    enum class Status{
        Losing,Lost,Unavailable,Available
    }
}
// this class is observing Internet connection in app at lifetime