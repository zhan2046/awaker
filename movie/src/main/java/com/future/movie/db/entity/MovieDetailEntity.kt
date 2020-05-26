package com.future.movie.db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@Entity(tableName = "movie_detail_entity")
data class MovieDetailEntity(
    @PrimaryKey
    var id: String,
    var title: String,
    var type: String,
    var content: String,
    var image: String,
    var logo: String,
    var timestamp: Long,
    var introduceList: ArrayList<IntroduceItem>,
    var videoList: ArrayList<VideoItem>
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readLong(),
        source.createTypedArrayList(IntroduceItem.CREATOR),
        source.createTypedArrayList(VideoItem.CREATOR)
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
        writeTypedList(introduceList)
        writeTypedList(videoList)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MovieDetailEntity> = object : Parcelable.Creator<MovieDetailEntity> {
            override fun createFromParcel(source: Parcel): MovieDetailEntity = MovieDetailEntity(source)
            override fun newArray(size: Int): Array<MovieDetailEntity?> = arrayOfNulls(size)
        }
    }
}