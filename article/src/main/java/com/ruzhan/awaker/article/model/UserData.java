package com.ruzhan.awaker.article.model;

import android.arch.persistence.room.Embedded;

public class UserData {

    public String open_id;
    @Embedded(prefix = "user_info_data_auth")
    public UserAuth auth;
    public int timestamp;
}
