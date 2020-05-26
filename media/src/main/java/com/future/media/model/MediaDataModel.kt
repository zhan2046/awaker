package com.future.media.model

import android.os.Parcel
import android.os.Parcelable

data class MediaDataModel(
    var id: String = "",
    var type: String = "",
    var url: String = "",
    var name: String = ""
) : Parcelable {

    fun getMediaId() = name.plus(url)

    fun getMediaType() = type

    fun getMediaUrl() = url

    fun getMediaName() = name

    constructor(source: Parcel) : this(
        source.readString() ?: "",
        source.readString() ?: "",
        source.readString() ?: "",
        source.readString() ?: ""
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(type)
        writeString(url)
        writeString(name)
    }

    companion object {

        const val MEDIA_TYPE = "MEDIA_TYPE"
        const val VIDEO = "VIDEO"
        const val AUDIO = "AUDIO"
        const val MEDIA_LIST = "MEDIA_LIST"

        @JvmField
        val CREATOR: Parcelable.Creator<MediaDataModel> = object : Parcelable.Creator<MediaDataModel> {
            override fun createFromParcel(source: Parcel): MediaDataModel = MediaDataModel(source)
            override fun newArray(size: Int): Array<MediaDataModel?> = arrayOfNulls(size)
        }
    }
}