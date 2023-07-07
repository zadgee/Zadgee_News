package authorization.mappers
import authorization.models.DefaultAuthorizationData
import authorization.models.GmailAuthorizationUserData
import com.google.firebase.auth.FirebaseUser


fun FirebaseUser.toGmailUser()= GmailAuthorizationUserData(
    name = displayName,
    picture = photoUrl.toString(),
    id = uid
)

fun FirebaseUser.toUser() = DefaultAuthorizationData(
    name = displayName!!
)


