package authorization.state

import domain.state.SendingEmailsState

interface EmailListsSenderRepository {
    suspend fun sendEmailVerification(): SendingEmailsState
    suspend fun sendResetPasswordEmail(email: String): SendingEmailsState
}