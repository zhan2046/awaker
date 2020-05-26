package com.future.day.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object DayClient {

    private const val HOST = "http://idaily-cdn.appcloudcdn.com/api/"
    private var api: DayApi? = null

    fun get(): DayApi {
        return api ?: synchronized(DayClient::class) {
            api ?: create().also { api = it }
        }
    }

    private fun create(): DayApi {
        val client = Retrofit.Builder().baseUrl(HOST)
                .client(OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return client.create(DayApi::class.java)
    }
}