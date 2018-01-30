package com.future.awaker.db.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.future.awaker.db.entity.CommentEntity;

import io.reactivex.Flowable;

@Dao
public interface CommentListDao {

    @Query("SELECT * FROM comment_entity WHERE id = :id")
    Flowable<CommentEntity> loadCommentEntity(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCommentEntity(CommentEntity commentEntity);
}
