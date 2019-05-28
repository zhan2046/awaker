package com.ruzhan.awaker.article.network

import com.google.gson.annotations.SerializedName

class HttpResult<T> {

    @SerializedName("info")
    var info: String? = null
    @SerializedName("code")
    var code: String? = null
    @SerializedName("data")
    var data: T? = null
}
