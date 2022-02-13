package com.future.movie.db.entity

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class VideoItem(
    var title: String,
    var image: String,
    var m3u8Url: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString() ?: "",
        source.readString() ?: "",
        source.readString() ?: ""
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeString(image)
        writeString(m3u8Url)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<VideoItem> = object : Parcelable.Creator<VideoItem> {
            override fun createFromParcel(source: Parcel): VideoItem = VideoItem(source)
            override fun newArray(size: Int): Array<VideoItem?> = arrayOfNulls(size)
        }
    }
}