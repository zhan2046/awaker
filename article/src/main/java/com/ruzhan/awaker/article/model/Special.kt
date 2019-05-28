package com.ruzhan.awaker.article.model

class Special {

    var id: String? = null
    var title: String? = null
    var cover_id: String? = null
    var special_id: String? = null
    var create_time: String? = null
    var cover: String? = null
    var position: Int = 0

    companion object {

        val NORMAL = 0
        val UFO = 16
        val THEORY = 17
        val SPIRIT = 18
        val FREE = 19
    }
}
