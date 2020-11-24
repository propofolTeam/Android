package com.example.propofolteam.application

import android.app.Application
import com.example.propofolteam.data.NullOnEmptyConverterFactory
import com.example.propofolteam.network.BaseUrl
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PropofolApplication : Application() {

    lateinit var retrofit: Retrofit
    private var baseUrl: BaseUrl = BaseUrl()

    override fun onCreate() {
        super.onCreate()
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