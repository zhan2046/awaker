package com.ruzhan.awaker.article.util;

import android.text.TextUtils;

import com.ruzhan.awaker.article.model.NewEle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

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
            if (NewEle.Companion.getTAG_P().equals(tagName)) {
                //text ot img
                Element elementImg = item.getElementsByTag(NewEle.Companion.getIMG()).first();
                if (elementImg != null) {
                    //img
                    String imgUrl = elementImg.attr(NewEle.Companion.getSRC());

                    NewEle newEleImg = new NewEle();
                    newEleImg.setType(NewEle.Companion.getTYPE_IMG());
                    newEleImg.setImgUrl(imgUrl);
                    newEleImg.setHtml(item.outerHtml());
                    newEleList.add(newEleImg);
                }

                String text = item.text();
                if (!TextUtils.isEmpty(text)) {
                    //text
                    newEleList.addAll(handlerTextType(text, item.outerHtml()));
                }

            } else if (NewEle.Companion.getTAG_IFRAME().equals(tagName)
                    || NewEle.Companion.getTAG_EMBED().equals(tagName)) {
                //video
                String videoUrl = item.attr(NewEle.Companion.getSRC());

                NewEle newEleVideo = new NewEle();
                newEleVideo.setType(NewEle.Companion.getTYPE_VIDEO());
                newEleVideo.setVideoUrl(videoUrl);
                newEleVideo.setHtml(item.outerHtml());
                newEleList.add(newEleVideo);
            }
        }
        return newEleList;
    }

    public static String htmlToVideoUrl(String html) {
        String url = "";
        if (!TextUtils.isEmpty(html)) {
            // iframe
            Document document = Jsoup.parseBodyFragment(html);
            Element iFrameElement = document.select("iframe").first();
            if (iFrameElement != null) {
                url = iFrameElement.attr(NewEle.Companion.getSRC());
            }
            // embed
            if (TextUtils.isEmpty(url)) {
                Element embedElement = document.select("embed").first();
                if (embedElement != null) {
                    url = embedElement.attr(NewEle.Companion.getSRC());
                }
            }
        }
        return url;
    }


    public static List<NewEle> handlerTextType(String text, String outerHtml) {
        List<NewEle> newEleList = new ArrayList<>();
        if (!TextUtils.isEmpty(text)) {
            String removeLogo = text.replaceAll(NewEle.Companion.getTAG_LOGO(), "");
            if (removeLogo.contains(NewEle.Companion.getTAG_PERIOD())) { // 存在多个句号，进行分割

                String[] splitArr = removeLogo.split(NewEle.Companion.getTAG_PERIOD());
                for (String str : splitArr) {
                    if (str.length() > 1) {
                        NewEle newEleText = new NewEle();
                        newEleText.setType(NewEle.Companion.getTYPE_TEXT());
                        String itemStr = str.concat(NewEle.Companion.getTAG_PERIOD());
                        newEleText.setText(itemStr.trim());
                        newEleText.setHtml(outerHtml);
                        newEleList.add(newEleText);
                    }
                }

            } else {
                NewEle newEleText = new NewEle();
                newEleText.setType(NewEle.Companion.getTYPE_TEXT());
                newEleText.setText(text.trim());
                newEleText.setHtml(outerHtml);
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
