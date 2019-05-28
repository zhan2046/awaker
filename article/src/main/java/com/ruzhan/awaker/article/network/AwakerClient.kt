package com.ruzhan.awaker.article.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AwakerClient {

    private val HOST = "http://www.awaker.cn/api/"

    private var api: AwakerApi? = null

    fun get(): AwakerApi {
        if (api == null) {
            synchronized(AwakerClient::class.java) {
                if (api == null) {
                    val client = Retrofit.Builder().baseUrl(HOST)
                            .client(HttpClient.getHttpClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build()
                    api = client.create(AwakerApi::class.java)
                }
            }
        }
        return api!!
    }
}
