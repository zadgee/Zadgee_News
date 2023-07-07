package authorization

import domain.state.SignOutState

interface SignOutRepository {
    suspend fun signOutWithGmail(): SignOutState
    suspend fun signOutWhileUsingDefaultAuth(): SignOutState
}