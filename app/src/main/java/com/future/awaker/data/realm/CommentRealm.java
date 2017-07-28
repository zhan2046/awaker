package com.future.awaker.data.realm;


import com.future.awaker.data.Comment;

import io.realm.RealmObject;

public class CommentRealm extends RealmObject {

    public String id;
    public String uid;
    public String app;
    public String mod;
    public String row_id;
    public String parse;
    public String content;
    public String create_time;
    public String pid;
    public String status;
    public String ip;
    public String area;
    public String up;
    public String down;
    public String type;
    public String sina_name;
    public String sina_avatar;
    public String sina_url;
    public UserRealm user;
    public CommentTitleRealm newstitle;

    public static Comment setCommentRealm(CommentRealm realm) {
        if (realm == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.id = realm.id;
        comment.uid = realm.uid;
        comment.app = realm.app;
        comment.mod = realm.mod;
        comment.row_id = realm.row_id;
        comment.parse = realm.parse;
        comment.content = realm.content;
        comment.create_time = realm.create_time;
        comment.pid = realm.pid;
        comment.status = realm.status;
        comment.ip = realm.ip;
        comment.area = realm.area;
        comment.up = realm.up;
        comment.down = realm.down;
        comment.type = realm.type;
        comment.sina_name = realm.sina_name;
        comment.sina_avatar = realm.sina_avatar;
        comment.sina_url = realm.sina_url;
        comment.user = UserRealm.setUserRealm(realm.user);
        comment.newstitle = CommentTitleRealm.setCommentTitleRealm(realm.newstitle);
        return comment;
    }

    public static CommentRealm setComment(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentRealm realm = new CommentRealm();
        realm.id = comment.id;
        realm.uid = comment.uid;
        realm.app = comment.app;
        realm.mod = comment.mod;
        realm.row_id = comment.row_id;
        realm.parse = comment.parse;
        realm.content = comment.content;
        realm.create_time = comment.create_time;
        realm.pid = comment.pid;
        realm.status = comment.status;
        realm.ip = comment.ip;
        realm.area = comment.area;
        realm.up = comment.up;
        realm.down = comment.down;
        realm.type = comment.type;
        realm.sina_name = comment.sina_name;
        realm.sina_avatar = comment.sina_avatar;
        realm.sina_url = comment.sina_url;
        realm.user = UserRealm.setUser(comment.user);
        realm.newstitle = CommentTitleRealm.setCommentTitle(comment.newstitle);
        return realm;
    }
}
