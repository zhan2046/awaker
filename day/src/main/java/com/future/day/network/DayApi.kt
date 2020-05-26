package com.future.day.network

import com.future.day.model.DayNewModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DayApi {

    @GET("list/v3/android/zh-hans")
    fun getDayNewList(@Query("page") page: Int, @Query("ver") ver: String,
                      @Query("app_ver") appVer: String): Single<List<DayNewModel>>
}