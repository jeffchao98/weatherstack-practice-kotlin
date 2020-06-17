package com.scchao.wtrstkpractice.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { WeatherClient() }
    factory { provideOkHttpClient(get()) }
    factory { provideRestApi(get()) }
    single { provideRetrofit(get()) }
}

fun baseUrl(): String {
    return "http://api.weatherstack.com/"
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(baseUrl()).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(authInterceptor: WeatherClient): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()
}

fun provideRestApi(retrofit: Retrofit): WeatherAPI = retrofit.create(WeatherAPI::class.java)

class WeatherClient : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .build()
        return chain.proceed(newRequest)
    }
}
