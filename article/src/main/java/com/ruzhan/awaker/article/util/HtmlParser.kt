package com.ruzhan.awaker.article.util

import android.text.TextUtils

import com.ruzhan.awaker.article.model.NewEle

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import java.util.ArrayList

object HtmlParser {

    /**
     * not run in main thread, need new thread
     */
    fun htmlToList(html: String): List<NewEle>? {
        if (TextUtils.isEmpty(html)) {
            return null
        }
        val newEleList = ArrayList<NewEle>()
        val document = Jsoup.parseBodyFragment(html)
        val all = document.allElements
        for (item in all) {
            val tagName = item.tagName()
            if (NewEle.TAG_P == tagName) {
                //text ot img
                val elementImg = item.getElementsByTag(NewEle.IMG).first()
                if (elementImg != null) {
                    //img
                    val imgUrl = elementImg.attr(NewEle.SRC)

                    val newEleImg = NewEle()
                    newEleImg.type = NewEle.TYPE_IMG
                    newEleImg.imgUrl = imgUrl
                    newEleImg.html = item.outerHtml()
                    newEleList.add(newEleImg)
                }

                val text = item.text()
                if (!TextUtils.isEmpty(text)) {
                    //text
                    newEleList.addAll(handlerTextType(text, item.outerHtml()))
                }

            } else if (NewEle.TAG_IFRAME == tagName || NewEle.TAG_EMBED == tagName) {
                //video
                val videoUrl = item.attr(NewEle.SRC)

                val newEleVideo = NewEle()
                newEleVideo.type = NewEle.TYPE_VIDEO
                newEleVideo.videoUrl = videoUrl
                newEleVideo.html = item.outerHtml()
                newEleList.add(newEleVideo)
            }
        }
        return newEleList
    }

    fun htmlToVideoUrl(html: String): String? {
        var url = ""
        if (!TextUtils.isEmpty(html)) {
            // iframe
            val document = Jsoup.parseBodyFragment(html)
            val iFrameElement = document.select("iframe").first()
            if (iFrameElement != null) {
                url = iFrameElement.attr(NewEle.SRC)
            }
            // embed
            if (TextUtils.isEmpty(url)) {
                val embedElement = document.select("embed").first()
                if (embedElement != null) {
                    url = embedElement.attr(NewEle.SRC)
                }
            }
        }
        return url
    }


    fun handlerTextType(text: String, outerHtml: String): List<NewEle> {
        val newEleList = ArrayList<NewEle>()
        if (!TextUtils.isEmpty(text)) {
            val removeLogo = text.replace(NewEle.TAG_LOGO.toRegex(), "")
            if (removeLogo.contains(NewEle.TAG_PERIOD)) { // 存在多个句号，进行分割

                val splitArr = removeLogo.split(NewEle.TAG_PERIOD.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (str in splitArr) {
                    if (str.length > 1) {
                        val newEleText = NewEle()
                        newEleText.type = NewEle.TYPE_TEXT
                        val itemStr = str + NewEle.TAG_PERIOD
                        newEleText.text = itemStr.trim { it <= ' ' }
                        newEleText.html = outerHtml
                        newEleList.add(newEleText)
                    }
                }

            } else {
                val newEleText = NewEle()
                newEleText.type = NewEle.TYPE_TEXT
                newEleText.text = text.trim { it <= ' ' }
                newEleText.html = outerHtml
                newEleList.add(newEleText)
            }
        }
        return newEleList
    }

    fun loadDataWith(loadData: String): String {
        val header = "<html><body>"
        val footer = "</body></html>"
        val sb = StringBuilder()
        sb.append(header)
        sb.append(loadData)
        sb.append(footer)
        return sb.toString()
    }

    fun loadDataWithVideo(loadData: String): String {
        val header = "<html><body><div><p><center>"
        val footer = "</center></p></div></body></html>"
        val sb = StringBuilder()
        sb.append(header)
        sb.append(loadData)
        sb.append(footer)
        return sb.toString()
    }
}
