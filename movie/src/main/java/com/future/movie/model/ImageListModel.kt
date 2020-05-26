package com.future.movie.model

import java.io.Serializable

data class ImageListModel(

        var title: String,
        var position: Int,
        var url: String,
        var imageList: ArrayList<String>
) : Serializable