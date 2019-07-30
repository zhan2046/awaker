package com.ruzhan.awaker.article.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ruzhan.awaker.article.db.converter.RoomDataConverter;
import com.ruzhan.awaker.article.model.Special;

import java.util.List;

@Entity(tableName = "special_list_entity")
public class SpecialListEntity {

    @NonNull
    @PrimaryKey
    public String id;
    @TypeConverters(RoomDataConverter.class)
    public List<Special> specialList;

    public SpecialListEntity(@NonNull String id, List<Special> specialList) {
        this.id = id;
        this.specialList = specialList;
    }
}
