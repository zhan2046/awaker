package com.feature.dayjson.network

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface FileDownloadApi {

    @Streaming
    @GET
    fun requestFileDownload(@Url url: String): Single<ResponseBody>
}