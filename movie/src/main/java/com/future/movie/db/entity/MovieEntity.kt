package com.future.movie.db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@Entity(tableName = "movie_entity")
data class MovieEntity(
    @PrimaryKey
    var id: String,
    var title: String,
    var type: String,
    var content: String,
    var image: String,
    var logo: String,
    var timestamp: Long
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readLong()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(title)
        writeString(type)
        writeString(content)
        writeString(image)
        writeString(logo)
        writeLong(timestamp)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MovieEntity> = object : Parcelable.Creator<MovieEntity> {
            override fun createFromParcel(source: Parcel): MovieEntity = MovieEntity(source)
            override fun newArray(size: Int): Array<MovieEntity?> = arrayOfNulls(size)
        }
    }
}