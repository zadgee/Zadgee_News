package domain.di.annotations

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GmailSignIn

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GmailSignUp