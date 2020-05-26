package com.future.movie.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.future.movie.db.entity.MovieDetailEntity
import io.reactivex.Flowable

@Dao
interface MovieDetailDao {

    @Query("SELECT * FROM movie_detail_entity WHERE id = :id")
    fun loadMovieDetailEntity(id: String): Flowable<MovieDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetailEntity(movieDetailEntity: MovieDetailEntity)
}