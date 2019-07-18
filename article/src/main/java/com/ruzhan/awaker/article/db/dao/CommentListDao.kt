package com.ruzhan.awaker.article.db.dao


import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.ruzhan.awaker.article.db.entity.CommentEntity

import io.reactivex.Flowable

@Dao
interface CommentListDao {

    @Query("SELECT * FROM comment_entity WHERE id = :id")
    fun loadCommentEntity(id: String): Flowable<CommentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCommentEntity(commentEntity: CommentEntity)
}
