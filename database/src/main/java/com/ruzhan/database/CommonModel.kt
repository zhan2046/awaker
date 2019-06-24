package com.ruzhan.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "common_model")
data class CommonModel(
        @PrimaryKey
        var id: Int,
        @ColumnInfo(name = "content") var content: String
)
