package com.ruzhan.awaker.article.model

class NewEle {

    var type: Int = 0
    var text: String? = null
    var isBold: Boolean = false
    var imgUrl: String? = null
    var videoUrl: String? = null
    var html: String? = null

    companion object {

        val TAG_PERIOD = "。"
        val TAG_LOGO = "觉醒字幕组"

        val TAG_P = "p"
        val TAG_IFRAME = "iframe"
        val TAG_EMBED = "embed"
        val TAG_STRONG = "strong"

        val IMG = "img"
        val SRC = "src"

        val TYPE_TEXT = 1000
        val TYPE_IMG = 1001
        val TYPE_VIDEO = 1002
    }
}
