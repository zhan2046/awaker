package com.feature.dayjson.network

import com.feature.dayjson.model.DayNewModel
import com.feature.dayjson.model.HttpResult
import com.feature.dayjson.model.MainModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DayBackupApi {

    @GET("day/{pageFileName}")
    fun getBackupDayNewList(@Path("pageFileName") pageFileName: String): Single<HttpResult<List<DayNewModel>>>

    @GET("day/{pageFileName}")
    fun getMainModel(@Path("pageFileName") pageFileName: String): Single<HttpResult<MainModel>>
}