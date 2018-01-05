package com.future.awaker.db.entity;

import com.future.awaker.data.User;


public class UserEntity {

    public String avatar32;
    public String avatar64;
    public String avatar128;
    public String avatar256;
    public String avatar512;
    public String uid;
    public String nickname;
    public String title;
    public String score1;
    public String real_nickname;

    public static UserEntity setUser(User user) {
        if (user == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.avatar32 = user.avatar32;
        userEntity.avatar64 = user.avatar64;
        userEntity.avatar128 = user.avatar128;
        userEntity.avatar256 = user.avatar256;
        userEntity.avatar512 = user.avatar512;
        userEntity.uid = user.uid;
        userEntity.nickname = user.nickname;
        userEntity.title = user.title;
        userEntity.score1 = user.score1;
        userEntity.real_nickname = user.real_nickname;
        return userEntity;
    }

    public static User setUserEntity(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        User user = new User();
        user.avatar32 = userEntity.avatar32;
        user.avatar64 = userEntity.avatar64;
        user.avatar128 = userEntity.avatar128;
        user.avatar256 = userEntity.avatar256;
        user.avatar512 = userEntity.avatar512;
        user.uid = userEntity.uid;
        user.nickname = userEntity.nickname;
        user.title = userEntity.title;
        user.score1 = userEntity.score1;
        user.real_nickname = userEntity.real_nickname;
        return user;
    }
}
