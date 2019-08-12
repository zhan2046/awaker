package com.feature.dayjson.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object DayBackupClient {

    private const val HOST = "https://raw.githubusercontent.com/ruzhan123/awaker/master/json/api/"
    private var api: DayBackupApi? = null

    fun get(): DayBackupApi {
        var api = api
        if (api == null) {
            synchronized(DayBackupClient::class) {
                if (api == null) {
                    val client = Retrofit.Builder().baseUrl(HOST)
                            .client(OkHttpClient.Builder().build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build()
                    api = client.create(DayBackupApi::class.java)
                    DayBackupClient.api = api
                }
            }
        }
        return DayBackupClient.api!!
    }
}