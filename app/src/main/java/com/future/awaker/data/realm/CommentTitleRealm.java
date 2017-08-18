package com.future.awaker.data.realm;

import com.future.awaker.data.CommentTitle;

import io.realm.RealmObject;

/**
 * Copyright ©2017 by ruzhan
 */

public class CommentTitleRealm extends RealmObject {

    public String title;

    public static CommentTitle setCommentTitleRealm(CommentTitleRealm realm) {
        if (realm == null) {
            return null;
        }
        CommentTitle commentTitle = new CommentTitle();
        commentTitle.title = realm.title;
        return commentTitle;
    }

    public static CommentTitleRealm setCommentTitle(CommentTitle commentTitle) {
        if (commentTitle == null) {
            return null;
        }
        CommentTitleRealm realm = new CommentTitleRealm();
        realm.title = commentTitle.title;
        return realm;
    }
}
