package com.future.awaker.db.entity;

import com.future.awaker.data.CoverUrl;


public class CoverUrlEntity {

    public String ori;
    public String thumb;
    public String banana;

    public static CoverUrlEntity setCoverUrl(CoverUrl coverUrl) {
        if (coverUrl == null) {
            return null;
        }
        CoverUrlEntity coverUrlEntity = new CoverUrlEntity();
        coverUrlEntity.ori = coverUrl.ori;
        coverUrlEntity.thumb = coverUrl.thumb;
        coverUrlEntity.banana = coverUrl.banana;
        return coverUrlEntity;
    }

    public static CoverUrl setCoverUrlEntity(CoverUrlEntity coverUrlEntity) {
        if (coverUrlEntity == null) {
            return null;
        }
        CoverUrl coverUrl = new CoverUrl();
        coverUrl.ori = coverUrlEntity.ori;
        coverUrl.thumb = coverUrlEntity.thumb;
        coverUrl.banana = coverUrlEntity.banana;
        return coverUrl;
    }
}
