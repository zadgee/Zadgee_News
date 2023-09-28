package domain.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import domain.api.GeoCodingAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import domain.api.NewsDisplayingAPI
import domain.di.annotations.GeoCodingAnnotation
import domain.di.annotations.NewsAnnotation
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }



    @Provides
    @Singleton
    @GeoCodingAnnotation
    fun provideOkhttpForGeoCoding(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("key","7e3dda65b76a423bab2b49df65fb66e3")
                    .build()
                return@addInterceptor chain.proceed(request)
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()


    @Provides
    @Singleton
    @NewsAnnotation
    fun provideOkhttp(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("apiKey","088314e505984671a0336ca82eb02bec")
                    .build()
                return@addInterceptor chain.proceed(request)
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()



    @Provides
    @Singleton
    @NewsAnnotation
    fun provideGson():Gson = GsonBuilder()
            .setDateFormat("yyyy/mm/dd")
            .serializeNulls()
            .setLenient()
            .create()

    @Provides
    @Singleton
    @NewsAnnotation
    fun provideRetrofit(@NewsAnnotation okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @GeoCodingAnnotation
    fun provideRetrofitForGeoCoding(@GeoCodingAnnotation okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.opencagedata.com/geocode/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    @Singleton
    fun provideAPI(@NewsAnnotation retrofit: Retrofit): NewsDisplayingAPI =
        retrofit.create(NewsDisplayingAPI::class.java)


    @Provides
    @Singleton
    fun provideGeoCodingAPI(@GeoCodingAnnotation retrofit: Retrofit): GeoCodingAPI =
        retrofit.create(GeoCodingAPI::class.java)

}