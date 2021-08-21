package com.fatihhernn.recipes.di

import com.fatihhernn.recipes.data.database.sharedPref.SharedLocalDataSource
import com.fatihhernn.recipes.data.network.AuthApiService
import com.fatihhernn.recipes.data.network.AuthorizationApiService
import com.fatihhernn.recipes.util.Constants.Companion.BASE_URL
import com.fatihhernn.recipes.data.network.FoodRecipesApi
import com.fatihhernn.recipes.data.remote.AuthorizationRemoteDataSource
import com.fatihhernn.recipes.data.remote.RemoteDataSource
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient():OkHttpClient{
        return  OkHttpClient.Builder()
            .readTimeout(15,TimeUnit.SECONDS)
            .connectTimeout(15,TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory():GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ):Retrofit{
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit:Retrofit): FoodRecipesApi {
        return  retrofit.create(FoodRecipesApi::class.java)
    }

    @Provides
    fun provideRemoteDataSource(apiService: FoodRecipesApi,rickApiService: AuthApiService): RemoteDataSource {
        return RemoteDataSource(apiService,rickApiService)
    }

    @Provides
    @ApiEndPoint
    fun provideApiString():String{
        return "https://dist-learn.herokuapp.com/api/"
    }

    /** AUTH API, 3.PARTY OTHER API IMPLEMENTATION */

    @Provides
    @ApiRetrofit
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson, @ApiEndPoint endPoint: String):Retrofit{
        return Retrofit.Builder()
            .baseUrl(endPoint)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideAuthInterceptorOkHttpClient(
        sharedlocalDataSource: SharedLocalDataSource
    ): AuthOkHttpClient {
        return provideAuthOkHttpClient(OkHttpClient.Builder()
            .addInterceptor {
                val token = sharedlocalDataSource.getToken()
                val request = it.request().newBuilder().addHeader("Authorization", token!!).build()
                it.proceed(request)
            }
            .build())
    }
    private fun provideAuthOkHttpClient(okHttpClient: OkHttpClient): AuthOkHttpClient {
        return AuthOkHttpClient(okHttpClient)
    }

    @Provides
    fun provideAuthService(@ApiRetrofit retrofit: Retrofit):AuthApiService{
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    fun provideAuthorizationRemoteDataSource(
        authorizationAPIService: AuthorizationApiService,
    ): AuthorizationRemoteDataSource {
        return AuthorizationRemoteDataSource(authorizationAPIService)
    }

    @Provides
    fun provideGson():Gson{
        return Gson()
    }

    @Provides
    fun provideAuthApiService(@AuthRetrofit retrofit: Retrofit): AuthorizationApiService {
        return retrofit.create(AuthorizationApiService::class.java)
    }
    @Provides
    @AuthRetrofit
    fun provideAuthRetrofit(
        authOkHttpClient: AuthOkHttpClient,
        gson: Gson,
        endPoint: EndPoint
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endPoint.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(authOkHttpClient.okHttpClient)
            .build()
    }

    @Provides
    fun provideEndPoint(): EndPoint {
        return EndPoint("https://dist-learn.herokuapp.com/api/")
    }
}
data class AuthOkHttpClient(val okHttpClient: OkHttpClient)

data class EndPoint(val url: String)


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiEndPoint

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofit

