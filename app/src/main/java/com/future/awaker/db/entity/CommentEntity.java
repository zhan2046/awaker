package com.future.awaker.db.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.future.awaker.data.Comment;
import com.future.awaker.db.converter.RoomDataConverter;

import java.util.List;

@Entity(tableName = "comment_entity")
public class CommentEntity {

    @NonNull
    @PrimaryKey
    public String id;
    @TypeConverters(RoomDataConverter.class)
    public List<Comment> commentList;

    @Ignore
    public CommentEntity() {

    }

    public CommentEntity(@NonNull String id, List<Comment> commentList) {
        this.id = id;
        this.commentList = commentList;
    }
}
