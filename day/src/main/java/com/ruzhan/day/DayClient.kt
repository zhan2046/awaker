package com.ruzhan.day

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object DayClient {

    private const val HOST = "http://idaily-cdn.appcloudcdn.com/api/"
    private var api: DayApi? = null

    fun get(): DayApi {
        var api = api
        if (api == null) {
            synchronized(DayClient::class.java) {
                if (api == null) {
                    val client = Retrofit.Builder().baseUrl(HOST)
                            .client(OkHttpClient.Builder().build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build()
                    api = client.create(DayApi::class.java)
                    this.api = api
                }
            }
        }
        return this.api!!
    }
}