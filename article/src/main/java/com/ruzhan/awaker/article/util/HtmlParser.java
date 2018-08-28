package com.ruzhan.awaker.article.util;

import android.text.TextUtils;

import com.ruzhan.awaker.article.model.NewEle;

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

    private HtmlParser() {
    }

    /**
     * not run in main thread, need new thread
     */
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
                    newEleList.addAll(handlerTextType(text, item.outerHtml()));
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


    public static List<NewEle> handlerTextType(String text, String outerHtml) {
        List<NewEle> newEleList = new ArrayList<>();
        if (!TextUtils.isEmpty(text)) {
            String removeLogo = text.replaceAll(NewEle.TAG_LOGO, "");
            if (removeLogo.contains(NewEle.TAG_PERIOD)) { // 存在多个句号，进行分割

                String[] splitArr = removeLogo.split(NewEle.TAG_PERIOD);
                for (String str : splitArr) {
                    if (str.length() > 1) {
                        NewEle newEleText = new NewEle();
                        newEleText.type = NewEle.TYPE_TEXT;
                        String itemStr = str.concat(NewEle.TAG_PERIOD);
                        newEleText.text = itemStr.trim();
                        newEleText.html = outerHtml;
                        newEleList.add(newEleText);
                    }
                }

            } else {
                NewEle newEleText = new NewEle();
                newEleText.type = NewEle.TYPE_TEXT;
                newEleText.text = text.trim();
                newEleText.html = outerHtml;
                newEleList.add(newEleText);
            }
        }
        return newEleList;
    }

    public static String loadDataWith(String loadData) {
        String header = "<html><body>";
        String footer = "</body></html>";
        StringBuilder sb = new StringBuilder();
        sb.append(header);
        sb.append(loadData);
        sb.append(footer);
        return sb.toString();
    }

    public static String loadDataWithVideo(String loadData) {
        String header = "<html><body><div><p><center>";
        String footer = "</center></p></div></body></html>";
        StringBuilder sb = new StringBuilder();
        sb.append(header);
        sb.append(loadData);
        sb.append(footer);
        return sb.toString();
    }
}
