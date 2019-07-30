package com.ruzhan.awaker.article.model;

import androidx.room.Embedded;

public class UserData {

    public String open_id;
    @Embedded(prefix = "user_info_data_auth")
    public UserAuth auth;
    public int timestamp;
}
