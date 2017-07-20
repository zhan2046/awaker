package com.future.awaker.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Copyright ©2017 by Teambition
 */

public class CoverUrl extends RealmObject {

    @PrimaryKey
    public String ori;
    public String thumb;
    public String banana;

    public String getOri() {
        return ori;
    }

    public void setOri(String ori) {
        this.ori = ori;
    }
}
