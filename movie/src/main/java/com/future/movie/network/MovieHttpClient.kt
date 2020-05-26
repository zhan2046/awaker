package com.future.movie.network

import okhttp3.OkHttpClient

class MovieHttpClient private constructor() {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

    companion object {

        private var INSTANCE: MovieHttpClient? = null

        fun get(): MovieHttpClient = INSTANCE
                ?: synchronized(MovieHttpClient::class) {
                    INSTANCE
                        ?: MovieHttpClient().also {
                        INSTANCE = it
                    }
                }

        @JvmStatic
        fun getCommonHttpClient(): OkHttpClient = get().okHttpClient
    }
}
