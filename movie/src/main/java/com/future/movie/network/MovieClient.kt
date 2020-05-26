package com.future.movie.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MovieClient {

    @Volatile
    private var api: MovieApi? = null

    fun get(): MovieApi = api
        ?: synchronized(MovieClient::class) {
        api
            ?: getMovieApi().also { api = it }
    }

    private fun getMovieApi(): MovieApi {
        val client = Retrofit.Builder().baseUrl(MovieApi.HOST)
                .client(MovieHttpClient.getCommonHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return client.create(MovieApi::class.java)
    }
}
