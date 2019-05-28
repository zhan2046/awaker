package com.ruzhan.awaker.article.network

import okhttp3.OkHttpClient

class HttpClient private constructor() {

    private val okHttpClient: OkHttpClient

    init {
        okHttpClient = OkHttpClient.Builder().build()
    }

    companion object {

        private var httpClient: HttpClient? = null

        fun get(): HttpClient {
            if (httpClient == null) {
                synchronized(HttpClient::class.java) {
                    if (httpClient == null) {
                        httpClient = HttpClient()
                    }
                }
            }
            return httpClient!!
        }

        fun getHttpClient(): OkHttpClient {
            return get().okHttpClient
        }
    }
}
