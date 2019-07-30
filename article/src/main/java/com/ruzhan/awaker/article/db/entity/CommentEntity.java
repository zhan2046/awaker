package com.ruzhan.awaker.article.db.entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ruzhan.awaker.article.db.converter.RoomDataConverter;
import com.ruzhan.awaker.article.model.Comment;

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
