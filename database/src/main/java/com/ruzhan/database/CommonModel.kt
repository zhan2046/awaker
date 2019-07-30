package com.ruzhan.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "common_model")
data class CommonModel(
        @PrimaryKey
        var id: Int,
        @ColumnInfo(name = "content") var content: String
)
