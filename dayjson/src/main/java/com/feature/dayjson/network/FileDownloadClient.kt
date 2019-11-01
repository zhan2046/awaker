package com.feature.dayjson.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object FileDownloadClient {

    private const val DEFAULT_TIME_OUT = 60L
    private var api: FileDownloadApi? = null

    fun get(): FileDownloadApi {
        var api = api
        if (api == null) {
            synchronized(FileDownloadClient::class) {
                if (api == null) {
                    val client = Retrofit.Builder().baseUrl("http://pic.yupoo.com/")
                            .client(OkHttpClient.Builder()
                                    .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                                    .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                                    .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                                    .build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build()
                    api = client.create(FileDownloadApi::class.java)
                    FileDownloadClient.api = api
                }
            }
        }
        return FileDownloadClient.api!!
    }
}