package com.future.awaker.data.realm;

import com.future.awaker.data.Comment;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Copyright ©2017 by ruzhan
 */

public class CommentHotRealm extends RealmObject {

    public static final String ID = "comment_hot_id";
    public static final String COMMENT_HOT = "comment_hot";

    @PrimaryKey
    private String comment_hot_id;
    private RealmList<CommentRealm> commentList;

    public static List<Comment> getList(RealmList<CommentRealm> list) {
        if (list == null) {
            return null;
        }
        List<Comment> newsList = new ArrayList<>();
        for (CommentRealm item : list) {
            Comment comment = CommentRealm.setCommentRealm(item);
            newsList.add(comment);
        }
        return newsList;
    }

    public static RealmList<CommentRealm> getRealmList(List<Comment> list) {
        if (list == null) {
            return null;
        }
        RealmList<CommentRealm> newsList = new RealmList<>();
        for (Comment item : list) {
            CommentRealm commentRealm = CommentRealm.setComment(item);
            newsList.add(commentRealm);
        }
        return newsList;
    }

    public String getComment_hot_id() {
        return comment_hot_id;
    }

    public void setComment_hot_id(String comment_hot_id) {
        this.comment_hot_id = comment_hot_id;
    }

    public RealmList<CommentRealm> getCommentList() {
        return commentList;
    }

    public void setCommentList(RealmList<CommentRealm> commentList) {
        this.commentList = commentList;
    }
}
