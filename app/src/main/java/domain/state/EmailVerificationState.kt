package domain.state

sealed class EmailVerificationState {
    object Loading : EmailVerificationState()
    object Verified : EmailVerificationState()
    object NotVerified : EmailVerificationState()
}
