package com.future.movie.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.future.movie.db.entity.MovieEntity
import io.reactivex.Flowable

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_entity ORDER BY id DESC")
    fun loadMovieEntityList(): Flowable<List<MovieEntity>>

    @Query("SELECT * FROM movie_entity WHERE type=:type ORDER BY id DESC")
    fun loadMovieEntityList(type: String): Flowable<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieEntityList(dayNewList: List<MovieEntity>)
}