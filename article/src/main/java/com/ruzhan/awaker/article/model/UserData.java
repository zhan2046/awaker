package com.ruzhan.awaker.article.model;

import android.arch.persistence.room.Embedded;

/**
 * Copyright ©2017 by ruzhan
 */

public class UserData {

    public String open_id;
    @Embedded(prefix = "user_info_data_auth")
    public UserAuth auth;
    public int timestamp;
}
