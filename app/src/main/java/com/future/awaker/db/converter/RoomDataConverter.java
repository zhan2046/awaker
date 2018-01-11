package com.future.awaker.db.converter;

import android.arch.persistence.room.TypeConverter;

import com.future.awaker.data.Special;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.List;


public class RoomDataConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static List<Special> toSpecialList(String json) {
        return  new Gson().fromJson(json, new TypeToken<List<Special>>() {
        }.getType());
    }

    @TypeConverter
    public static String toSpecialListJson(List<Special> specialList) {
        return new Gson().toJson(specialList);
    }
}
