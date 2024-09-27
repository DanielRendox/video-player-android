package com.rendox.videoplayer.network.di

import com.rendox.videoplayer.BuildConfig
import com.rendox.videoplayer.network.GitHubApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val networkApiModule = module {
    single<Json> {
        Json { ignoreUnknownKeys = true }
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    this.addNetworkInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BASIC
                        },
                    )
                }
            }
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val urlWithQueryParam = originalRequest.url.newBuilder()
                    .addQueryParameter("ref", "main")
                    .build()
                val requestWithHeadersAndQueryParam = originalRequest.newBuilder()
                    .header("Accept", "application/vnd.github.raw+json")
                    .url(urlWithQueryParam)
                    .build()
                chain.proceed(requestWithHeadersAndQueryParam)
            }
            .build()
    }

    single<GitHubApi> {
        val client = get<OkHttpClient>()
        val json = get<Json>()
        Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.github.com/repos/DanielRendox/video-player-android/contents/assets/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(GitHubApi::class.java)
    }
}