package com.future.awaker.data.realm;

import com.future.awaker.data.Comment;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Copyright ©2017 by Teambition
 */

public class CommentPageRealm extends RealmObject {

    public static final String ID = "comment_page_id";
    public static final String COMMENT_PAGE = "comment_page";

    @PrimaryKey
    private String comment_page_id;
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

    public String getComment_page_id() {
        return comment_page_id;
    }

    public void setComment_page_id(String comment_page_id) {
        this.comment_page_id = comment_page_id;
    }

    public RealmList<CommentRealm> getCommentList() {
        return commentList;
    }

    public void setCommentList(RealmList<CommentRealm> commentList) {
        this.commentList = commentList;
    }
}
