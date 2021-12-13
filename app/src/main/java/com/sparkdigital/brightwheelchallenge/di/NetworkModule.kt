package com.sparkdigital.brightwheelchallenge.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sparkdigital.brightwheelchallenge.BuildConfig.BASE_URL
import com.sparkdigital.brightwheelchallenge.BuildConfig.TOKEN
import com.sparkdigital.brightwheelchallenge.repository.network.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    @Provides
    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(
                    chain
                        .request()
                        .newBuilder()
                        .header("Authorization","token ${TOKEN}")
                        .header("Accept", "application/vnd.github.v3+json")
                        .build()
                )
            }
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .build()


    @Provides
    fun provideGson() = GsonBuilder().create()

    @Provides
    fun provideGithubService(retrofit: Retrofit) = retrofit.create(GithubService::class.java)

}