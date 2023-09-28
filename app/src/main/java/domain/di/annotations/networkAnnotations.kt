package domain.di.annotations

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeoCodingAnnotation