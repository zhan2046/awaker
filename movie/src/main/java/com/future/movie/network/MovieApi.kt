package com.future.movie.network

import com.future.movie.db.entity.MovieDetailEntity
import com.future.movie.db.entity.MovieEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface MovieApi {

    @GET("movie/{pageFileName}")
    fun getMovieList(@Path("pageFileName") pageFileName: String): Single<List<MovieEntity>>

    @GET("movie/detail/{detailFile}")
    fun getMovieDetail(@Path("detailFile") detailFile: String): Single<MovieDetailEntity>

    companion object {

        const val HOST = "https://raw.githubusercontent.com/nahzur-h/Movie/master/json/api/"
    }
}
