package domain.state

data class EmailVerificationWorkerState(
    val isEnqueued:Boolean = false,
    val isRunning:Boolean = false,
    val isSucceeded:Boolean = false,
    val isFailed:Boolean = false,
    val isBlocked:Boolean = false,
    val isCancelled:Boolean = false
)