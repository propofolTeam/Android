package com.example.propofolteam.application

import android.app.Application
import com.example.propofolteam.data.EmailLoginResponse
import com.example.propofolteam.data.NullOnEmptyConverterFactory
import com.example.propofolteam.network.BaseUrl
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Application : Application() {

    lateinit var retrofit: Retrofit
    private var baseUrl: BaseUrl = BaseUrl()

    override fun onCreate() {
        super.onCreate()
        val interceptor = Interceptor() {
            val token = EmailLoginResponse.instance?.accessToken
            val newRequest: Request
            if (token != null && !token.equals("")) {
                // Authorization 헤더에 토큰 추가
                newRequest = it.request().newBuilder().addHeader("Authentication", token).build()

            } else newRequest = it.request()
            it.proceed(newRequest)
        }
        Stetho.initializeWithDefaults(this)

        val httpClient = OkHttpClient.Builder()
        httpClient.addNetworkInterceptor(StethoInterceptor())
        val client = httpClient.build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl.BaseURL)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}