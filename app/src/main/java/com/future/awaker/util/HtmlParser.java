package com.future.awaker.util;

import android.text.TextUtils;

import com.future.awaker.data.NewEle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruzhan on 2017/7/15.
 */

public final class HtmlParser {

    private HtmlParser() {}

    public static List<NewEle> htmlToList(String html) {
        if (TextUtils.isEmpty(html)) {
            return null;
        }
        List<NewEle> newEleList = new ArrayList<>();
        Document document = Jsoup.parseBodyFragment(html);
        Elements all = document.getAllElements();
        for (Element item : all) {
            String tagName = item.tagName();
            if (NewEle.TAG_P.equals(tagName)) {
                //text ot img
                Element elementImg = item.getElementsByTag(NewEle.IMG).first();
                if (elementImg != null) {
                    //img
                    String imgUrl = elementImg.attr(NewEle.SRC);

                    NewEle newEleImg = new NewEle();
                    newEleImg.type = NewEle.TYPE_IMG;
                    newEleImg.imgUrl = imgUrl;
                    newEleImg.html = item.outerHtml();
                    newEleList.add(newEleImg);
                }

                String text = item.text();
                if (!TextUtils.isEmpty(text)) {
                    //text
                    NewEle newEleText = new NewEle();
                    newEleText.type = NewEle.TYPE_TEXT;
                    newEleText.text = text;
                    newEleText.html = item.outerHtml();
                    newEleList.add(newEleText);
                }

            } else if (NewEle.TAG_IFRAME.equals(tagName)) {
                //video
                String videoUrl = item.attr(NewEle.SRC);

                NewEle newEleVideo = new NewEle();
                newEleVideo.type = NewEle.TYPE_VIDEO;
                newEleVideo.videoUrl = videoUrl;
                newEleVideo.html = item.outerHtml();
                newEleList.add(newEleVideo);
            }
        }
        return newEleList;
    }


}
