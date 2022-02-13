package com.future.movie.db.entity

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class IntroduceItem(
    var type: Int,
    var text: String,
    var image: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString() ?: "",
        source.readString() ?: ""
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(type)
        writeString(text)
        writeString(image)
    }

    companion object {
        const val TEXT = 1000

        const val IMAGE = 1001

        @JvmField
        val CREATOR: Parcelable.Creator<IntroduceItem> = object : Parcelable.Creator<IntroduceItem> {
            override fun createFromParcel(source: Parcel): IntroduceItem = IntroduceItem(source)
            override fun newArray(size: Int): Array<IntroduceItem?> = arrayOfNulls(size)
        }
    }
}