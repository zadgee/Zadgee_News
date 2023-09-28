package domain.authorization.mappers
import domain.authorization.models.DefaultAuthorizationData
import domain.authorization.models.GmailAuthorizationUserData
import com.google.firebase.auth.FirebaseUser


fun FirebaseUser.toGmailUser()= GmailAuthorizationUserData(
    name = displayName,
    picture = photoUrl.toString(),
    id = uid
)

