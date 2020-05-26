package com.future.movie.network

import com.future.movie.db.MovieDatabase
import com.future.movie.db.entity.MovieDetailEntity
import com.future.movie.db.entity.MovieEntity
import com.future.movie.utils.ResUtils
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MovieRepository private constructor() {

    companion object {

        @JvmStatic
        fun get() = SingletonHolder.INSTANCE
    }

    private val api: MovieApi by lazy {
        MovieClient.get()
    }
    private val movieDatabase: MovieDatabase by lazy {
        MovieDatabase.get(ResUtils.getApp())
    }

    fun getMovieList(pageFileName: String): Single<List<MovieEntity>> {
        return api.getMovieList(pageFileName)
            .subscribeOn(Schedulers.io())
            .map { movieList ->
                insertMovieEntityList(movieList)
                movieList
            }
    }

    fun getMovieDetail(detailFile: String): Single<MovieDetailEntity> {
        return api.getMovieDetail(detailFile)
            .subscribeOn(Schedulers.io())
            .map { movieDetail ->
                insertMovieDetailEntity(movieDetail)
                movieDetail
            }
    }

    fun loadMovieEntityList(): Flowable<List<MovieEntity>> {
        return movieDatabase.movieDao().loadMovieEntityList()
    }

    fun loadMovieEntityList(tag: String): Flowable<List<MovieEntity>> {
        return movieDatabase.movieDao().loadMovieEntityList(tag)
    }

    private fun insertMovieEntityList(dayNewList: List<MovieEntity>) {
        movieDatabase.movieDao().insertMovieEntityList(dayNewList)
    }

    fun loadMovieDetailEntity(id: String): Flowable<MovieDetailEntity> {
        return movieDatabase.movieDetailDao().loadMovieDetailEntity(id)
    }

    private fun insertMovieDetailEntity(movieDetailEntity: MovieDetailEntity) {
        movieDatabase.movieDetailDao().insertMovieDetailEntity(movieDetailEntity)
    }

    private class SingletonHolder {
        companion object {
            val INSTANCE = MovieRepository()
        }
    }
}
