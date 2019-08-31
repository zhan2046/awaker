package com.feature.dayjson.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DayClient {

    private const val DEFAULT_TIME_OUT = 60L
    private const val HOST = "http://idaily-cdn.appcloudcdn.com/api/"
    private var api: DayApi? = null

    fun get(): DayApi {
        var api = api
        if (api == null) {
            synchronized(DayClient::class) {
                if (api == null) {
                    val client = Retrofit.Builder().baseUrl(HOST)
                            .client(OkHttpClient.Builder()
                                    .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                                    .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                                    .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                                    .build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build()
                    api = client.create(DayApi::class.java)
                    DayClient.api = api
                }
            }
        }
        return DayClient.api!!
    }
}